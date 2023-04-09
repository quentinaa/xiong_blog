package com.xiong.blog.service;

import com.xiong.blog.entity.UserEntity;

import java.util.List;

public interface FollowerService {
    //关注
    void clickFollow(Integer userId,Integer targetId);
    //判断是否被关注
    Boolean hasFollower(Integer userId,Integer targetId);
    //查询关注者列表
    List<UserEntity> getFollowers(Integer userId);
    //查询被关注者列表
    List<UserEntity> getFensList(Integer targetId);
}
