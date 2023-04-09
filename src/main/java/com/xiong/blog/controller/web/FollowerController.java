package com.xiong.blog.controller.web;

import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.FollowerService;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/3 14:25:47
 */
@Slf4j
@Controller
@RequestMapping("/follower")
public class FollowerController {
    @Autowired
    private FollowerService followerService;
    @Autowired
    private UserHelp userHelp;
    @GetMapping("/click/{targetId}")
    @ResponseBody
    public R clickFollow(@PathVariable Integer targetId){
        UserToken userToken= userHelp.get();
        if (userHelp.get()==null){
            throw  new BlogException("用户未登录");
        }
        followerService.clickFollow(userToken.getUid(),targetId);
        return R.ok();
    }
    @GetMapping("/findFollowerListByUserId/{userId}")
    public String findFollowerListByUserId(@PathVariable Integer userId , ModelMap modelMap){
        List<UserEntity> followers = followerService.getFollowers(userId);
        modelMap.addAttribute("followerList",followers);
        return "/site/follower";
    }

    @GetMapping("/findFansListByUserId/{targetId}")
    public String findFansListByUserId(@PathVariable Integer targetId,ModelMap modelMap){
        List<UserEntity> fensList = followerService.getFensList(targetId);
        modelMap.addAttribute("fensList",fensList);
        return "/site/fans";
    }
}
