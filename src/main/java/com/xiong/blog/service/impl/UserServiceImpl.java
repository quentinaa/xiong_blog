package com.xiong.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.blog.common.constants.UserConstants;
import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.exception.EmailException;
import com.xiong.blog.common.executor.ExecutorUtils;
import com.xiong.blog.common.status.BlogStatus;
import com.xiong.blog.common.utils.EmailUtils;
import com.xiong.blog.common.utils.PasswordUtils;
import com.xiong.blog.common.utils.UUIDUtils;
import com.xiong.blog.dao.UserDao;
import com.xiong.blog.dto.Email;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.UserService;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/1 16:23:58
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private EmailUtils emailUtils;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(UserEntity userEntity) {
        //判断邮箱是否被使用
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();
        queryWrapper.eq("email", userEntity.getEmail());
        UserEntity dbUserEntity = baseMapper.selectOne(queryWrapper);
        //判断查询的数据是否为空
        if (dbUserEntity != null) {
            //如果查到的邮箱不为空
            throw new EmailException("邮箱已经被使用");
        }
        //如果邮箱没有使用过 那就可以注册
        //设置状态是未激活
        userEntity.setStatu(BlogStatus.USER_UN_ACTIVATE.getCode());
        //设置激活码
        userEntity.setActivateCode(UUIDUtils.getUUid());
        //设置注册时间
        userEntity.setCreateTime(new Date());
        //对密码进行加密 设置密码
        userEntity.setPwd(PasswordUtils.encode(userEntity.getPwd()));
        //插入数据
        baseMapper.insert(userEntity);
        //redis存储数据
        stringRedisTemplate.opsForValue().set(
                //key
                String.format(UserConstants.ACTIVATEKEY, userEntity.getActivateCode())
                //value
                , userEntity.getId().toString()
                //超过时间
                , 1
                , TimeUnit.DAYS
        );
        //给用户发送激活邮件
        Email email = new Email();
        //设置邮件标题
        email.setTitle("blog激活码");
        //设置邮件收件人
        email.setToUser(userEntity.getEmail());
        //设置邮件内容
        email.setContent("<a href ='http://127.0.0.1:8001/user/activateUser/"
                + userEntity.getActivateCode() + "'>点击</a>这里激活");
        //使用异步的方式发送邮件
        ExecutorUtils.getExecutor().submit(() -> {
            emailUtils.sendEmail(email);
        });
    }

    @Override
    public void activateUser(String code) {
        //从redis中根据激活码 查询用户id
        String userId = stringRedisTemplate.opsForValue().get(String.format(UserConstants.ACTIVATEKEY, code));
        //判断用户id是否为空
        if (ObjectUtils.isEmpty(userId)) {
            throw new BlogException("激活码错误");
        }
        //根据用户id 激活码 去激活账号（修改MySQL）
        Integer integer = baseMapper.updateUserStatus(
                BlogStatus.USER_ACTIVATE.getCode(),
                userId,
                code);
        if (integer <= 0) {
            throw new BlogException("激活失败");
        }
        //删除redis中的激活码
        stringRedisTemplate.delete(String.format(UserConstants.ACTIVATEKEY, code));
    }

    @Override
    public UserToken login(String username, String pwd) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();
        queryWrapper.eq("username", username);
        UserEntity userEntity = baseMapper.selectOne(queryWrapper);
        //校验用户是否存在
        if (userEntity == null) {
            throw new BlogException("用户不存在");
        }
        //检测密码是否正确
        if (!PasswordUtils.checkpw(pwd, userEntity.getPwd())) {
            //检测失败
            throw new BlogException("密码错误");
        }
        //检测用户是否激活
        if (userEntity.getStatu() != BlogStatus.USER_ACTIVATE.getCode()) {
            throw new BlogException("用户没激活");
        }

        //生成用户令牌
        UserToken userToken = new UserToken();
        //设置生成时间
        userToken.setCreateTime(new Date());
        //生成token(唯一，复杂)
        userToken.setToken(UUIDUtils.getUUid());
        //设置用户id
        userToken.setUid(userEntity.getId());
        //设置用户名字
        userToken.setUname(userEntity.getUsername());
        //设置用户头像
        userToken.setHeaderUrl(userEntity.getHeadUrl());

        //获取当前时间 时间戳（距离1970年，某个时间的时间长度 （毫秒））
        long currentTime = System.currentTimeMillis();
        //2.十天的时间（毫秒）
        long userDefaultTimeOut = UserConstants.USER_DEFAULE_TIMEOUT * 1000;
        //3.什么时候超时
        long userTtl = currentTime + userDefaultTimeOut;
        //4.将时间戳转成 日期格式
        Date ttl = new Date(userTtl);
        //设置超时时间
        userToken.setTtl(ttl);
        //把令牌存到redis中
        String key = String.format(UserConstants.USER_LOGIN_TOKEN, userToken.getToken());
        redisTemplate.opsForValue().set(key, userToken, userDefaultTimeOut, TimeUnit.MILLISECONDS);
        return userToken;
    }

    @Override
    public void logout(String token) {
        String key = String.format(UserConstants.USER_LOGIN_TOKEN, token);
        //删除redis中对应的用户信息
        redisTemplate.delete(key);

    }

    @Override
    public void updatePassword(String oldPassword, String newPassword, String newPassword2, UserToken userToken) {
        //参数不能为空
        if (oldPassword == null
                || oldPassword.isEmpty()
                || newPassword == null
                || newPassword.isEmpty()
                || newPassword2 == null
                || newPassword2.isEmpty()) {
            throw new BlogException("参数为空");
        }
        //新密码要和确认密码一样
        if (!newPassword.equals(newPassword2)) {
            throw new BlogException("两次密码输入不一致");
        }
        //获取用户信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userToken.getUid());
        //根据id 去数据库查询对应信息
        UserEntity userEntity = baseMapper.selectOne(queryWrapper);
        if (userEntity == null) {
            throw new BlogException("数据异常");
        }
        //校验旧密码
        if (!PasswordUtils.checkpw(oldPassword, userEntity.getPwd())) {
            throw new BlogException("密码校验失败");
        }
        //修改数据用户的密码
        userEntity.setPwd(PasswordUtils.encode(newPassword));
        int i = baseMapper.updateById(userEntity);
        if (i <= 0) {
            throw new BlogException("密码修改失败");
        }
        //删除redis
        String key = String.format(UserConstants.USER_LOGIN_TOKEN, userToken.getToken());
        //删除redis中对应的用户信息
        redisTemplate.delete(key);
    }

    @Override
    public UserEntity findUserById(Integer uid) {
        //去redis中查询用户信息
        String key = String.format(UserConstants.USER_INFO_KEY, uid);
        UserEntity userEntity = (UserEntity) redisTemplate.opsForValue().get(key);
        //redis中不存在该信息 去数据库查
        if (userEntity==null){
            //锁 乐观锁  悲观锁
            //创建一个锁对象
            Lock lock=new ReentrantLock();
            try {
                //尝试获得锁资源
                boolean b=lock.tryLock();
                if (b){
                    //拿到锁之后
                    userEntity=getById(uid);
                    redisTemplate.opsForValue().set(key, userEntity,1,TimeUnit.DAYS);
                }else {
                    Thread.sleep(500);
                    return findUserById(uid);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //释放锁资源
                lock.unlock();
            }
        }
        //数据库查到该信息 将数据存入到redis中

        return userEntity;
    }
}
