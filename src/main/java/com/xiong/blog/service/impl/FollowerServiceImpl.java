package com.xiong.blog.service.impl;

import com.xiong.blog.common.constants.FollowerConstants;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.FollowerService;
import com.xiong.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/3 14:54:29
 */
@Service
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Override
    public void clickFollow(Integer userId, Integer targetId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //关注
                String followKey=String.format(FollowerConstants.FOLLOWER_KEY,userId);
                //被关注
                String fansKey=String.format(FollowerConstants.FANS_KEY,targetId);
                //判断是否关注/被关注
                Boolean isFollow = redisTemplate.opsForHash().hasKey(followKey, targetId.toString());
                Boolean isFans = redisTemplate.opsForHash().hasKey(fansKey, userId.toString());
                //开启事务
                operations.multi();
                if (isFollow&&isFans){
                    redisTemplate.opsForHash().delete(followKey, targetId.toString());
                    redisTemplate.opsForHash().delete(fansKey,userId.toString());
                }else {
                    UserEntity user = userService.findUserById(userId);
                    user.setCreateTime(new Date());
                    UserEntity target = userService.findUserById(targetId);
                    target.setCreateTime(new Date());
                    redisTemplate.opsForHash().put(followKey,targetId.toString(),target);
                    redisTemplate.opsForHash().put(fansKey,userId.toString(),user);

                }
                //提交事务
                operations.exec();
                return null;
            }
        });
    }

    @Override
    public Boolean hasFollower(Integer userId, Integer targetId) {
        String followKey = String.format(FollowerConstants.FOLLOWER_KEY, userId);

        return redisTemplate.opsForHash().hasKey(followKey,targetId.toString());
    }

    @Override
    public List<UserEntity> getFollowers(Integer userId) {
        String followKey = String.format(FollowerConstants.FOLLOWER_KEY, userId);
        return redisTemplate.opsForHash().values(followKey);
    }

    @Override
    public List<UserEntity> getFensList(Integer targetId) {
        String fensKey = String.format(FollowerConstants.FANS_KEY, targetId);
        return redisTemplate.opsForHash().values(fensKey);
    }
}
