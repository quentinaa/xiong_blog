package com.xiong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.vo.UserToken;

/**
 * @author xiong
 * @version 1.0
 * @description 处理业务的接口
 * @date 2023/3/1 16:20:36
 */
public interface UserService extends IService<UserEntity> {

    //注册接口
    void register(UserEntity userEntity);

    //激活用户
    void activateUser(String code);

    //登录接口
    UserToken login(String username,String pwd);

    //退出登录
    void logout(String token);

    //修改密码
    void updatePassword(String oldPassword, String newPassword, String newPassword2, UserToken userToken);
    //根据id查询用户信息
    UserEntity findUserById(Integer uid);


}
