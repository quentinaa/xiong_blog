package com.xiong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.blog.entity.FriendsEntity;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.vo.FriendsVo;

import java.util.List;

public interface FriendsService extends IService<FriendsEntity> {
    //查询好友列表
    List<FriendsVo> userFindByFriendsId(Integer userId);

    //查询好友数量
    Long FriendsCount(Integer userId);
    //添加好友
    void clickFriends(Integer userId,Integer targetId);
    //判断是否添加该好友
    Boolean hasFriends(Integer userId,Integer targetId);
    //删除好友
    void deleteFriendsById(Integer friendsId);
}
