package com.xiong.blog.controller.web;

import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.*;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/31 09:12:41
 */
@Slf4j
@Controller
@RequestMapping("/account")
public class UserInfoController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserHelp userHelp;
    @Autowired
    private ILikeService likeService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private FriendsService friendsService;
    @GetMapping("/info/{uid}")
    public String info(@PathVariable Integer uid, ModelMap modelMap){
        UserToken userToken=userHelp.get();
        if (userToken==null){
            throw new BlogException("用户未登录");
        }
        UserEntity userEntity = userService.getById(uid);
        Long userLikeCount = likeService.getUserLikeCount(uid);
        Long fansCount = userInfoService.getFansCount(uid);
        Long followerCount = userInfoService.getFollowerCount(uid);
        Long friendsCount = friendsService.FriendsCount(uid);

        modelMap.addAttribute("user",userEntity);
        modelMap.addAttribute("userLikeCount",userLikeCount);
        modelMap.addAttribute("fansCount",fansCount);
        modelMap.addAttribute("followerCount",followerCount);
        modelMap.addAttribute("friendsCount",friendsCount);
        Boolean hasFriends;
        hasFriends= friendsService.hasFriends(userToken.getUid(),uid);
        modelMap.addAttribute("hasFriends",hasFriends);
        Boolean hasFollowed=false;
        if (uid!=userToken.getUid())
            hasFollowed=followerService.hasFollower(userToken.getUid(),uid);
        modelMap.addAttribute("hasFollowed",hasFollowed);
        return "site/profile";
    }
}
