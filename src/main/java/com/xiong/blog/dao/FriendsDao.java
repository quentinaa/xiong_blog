package com.xiong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiong.blog.entity.FriendsEntity;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.vo.FriendsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/5 12:34:11
 */
@Mapper
public interface FriendsDao extends BaseMapper<FriendsEntity> {
    List<FriendsVo> userFindByFriendsId(@Param("userId") Integer userId);

    Long FriendsCount(@Param("userId") Integer userId);

    FriendsEntity hasFriends(@Param("userId") Integer userId,@Param("targetId") Integer targetId);

    void deleteFriendById(@Param("userId") Integer userId,@Param("friendsId") Integer friendsId);
}
