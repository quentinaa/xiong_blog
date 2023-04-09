package com.xiong.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.dao.FriendsDao;
import com.xiong.blog.entity.FriendsEntity;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.FollowerService;
import com.xiong.blog.service.FriendsService;
import com.xiong.blog.service.UserService;
import com.xiong.blog.vo.FriendsVo;
import com.xiong.blog.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Wrapper;
import java.util.Date;
import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/5 12:56:57
 */
@Service("FriendsService")

public class FriendsServiceImpl extends ServiceImpl<FriendsDao, FriendsEntity> implements FriendsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserHelp userHelp;
    @Override
    public List<FriendsVo> userFindByFriendsId(Integer userId) {
        List<FriendsVo> userEntityList = baseMapper.userFindByFriendsId(userId);
        return userEntityList;
    }

    @Override
    public Long FriendsCount(Integer userId) {
        return baseMapper.FriendsCount(userId);
    }

    @Override
    public void clickFriends(Integer userId, Integer targetId) {
        FriendsEntity friendsVo= new FriendsEntity();

        friendsVo.setAddTime(new Date());
        friendsVo.setUserId(userId);
        friendsVo.setUserFriendsId(targetId);
        friendsVo.setUserStatus(1);
        friendsVo.setUserNote(userService.findUserById(targetId).getUsername());
        Boolean hasFriends = hasFriends(userId, targetId);
        if (!hasFriends)
        baseMapper.insert(friendsVo);
    }

    @Override
    public Boolean hasFriends(Integer userId, Integer targetId) {
        FriendsEntity friendsVo = baseMapper.hasFriends(userId,targetId);
        return friendsVo==null?false:true;
    }

    @Override
    public void deleteFriendsById(Integer friendsId) {
       baseMapper.deleteFriendById(userHelp.get().getUid(),friendsId);
    }
}
