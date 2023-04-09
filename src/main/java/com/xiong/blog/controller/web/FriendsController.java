package com.xiong.blog.controller.web;

import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.FriendsService;
import com.xiong.blog.vo.FriendsVo;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/5 12:20:54
 */
@Slf4j
@Controller
@RequestMapping("/friends")
public class FriendsController {
    @Autowired
    private UserHelp userHelp;
    @Autowired
    FriendsService friendsService;
    @GetMapping("/findFriendsListByUserId/{userId}")
    public String findFriendsListByUserId(@PathVariable Integer userId, ModelMap modelMap){
        List<FriendsVo> friendsList = friendsService.userFindByFriendsId(userId);
        modelMap.addAttribute("friendsList",friendsList);
        return "/site/friends";
    }
    @GetMapping("/click/{targetId}")
    @ResponseBody
    public R clickFriends(@PathVariable Integer targetId){
        UserToken userToken= userHelp.get();
        if (userHelp.get()==null){
            throw  new BlogException("用户未登录");
        }
        friendsService.clickFriends(userToken.getUid(),targetId);

        return R.ok();
    }
    @GetMapping("/delete/{friendsId}")
    public String  deleteFriendsById(@PathVariable Integer friendsId){
        friendsService.deleteFriendsById(friendsId);
        return "/site/friends";
    }
}
