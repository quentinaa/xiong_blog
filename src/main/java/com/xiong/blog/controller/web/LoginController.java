package com.xiong.blog.controller.web;

import com.xiong.blog.common.constants.UserConstants;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.UserService;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/1 17:03:20
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserHelp userHelp;
    @PostMapping("/register")
    public R register(@RequestBody UserEntity userEntity){
        userService.register(userEntity);
        return R.ok();
    }
    @GetMapping("/activateUser/{code}")
    public String activateUser(@PathVariable String code){
        //判断激活码不为空
        if(!ObjectUtils.isEmpty(code)){
            //激活账号
            userService.activateUser(code);
        }
        System.out.println("激活码为空");
        return null;
    }
    @PostMapping("/login")
    public R login(@RequestBody UserEntity userEntity, HttpServletResponse response){
        //登录
        UserToken userToken = userService.login(userEntity.getUsername(), userEntity.getPwd());
        //把cookie存在浏览器中
        Cookie cookie=new Cookie(
                UserConstants.TOKEN_COOKIE_KEY,userToken.getToken()
        );
        //设置返回的页面
        cookie.setPath("/");
        cookie.setMaxAge(UserConstants.USER_DEFAULE_TIMEOUT);
        response.addCookie(cookie);
        return R.ok();
    }
    @GetMapping("/logout")
    public R logout(@CookieValue(name=UserConstants.TOKEN_COOKIE_KEY,defaultValue = "")String token,
                    HttpServletResponse response){
        userService.logout(token);
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return R.ok();
    }

    @PostMapping("/password")
    public R password(String oldPassword,
                      String newPassword,
                      String newPassword2,HttpServletResponse response){
       //获得用户信息
        UserToken userToken = userHelp.get();
        //修改密码
        userService.updatePassword(oldPassword,newPassword,newPassword2,userToken);
        userService.logout(userToken.getToken());
        //设置返回的页面
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return R.ok();
    }
}
