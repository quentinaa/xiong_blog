package com.xiong.blog.inteceprot;

import com.xiong.blog.common.constants.UserConstants;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.CookieUtils;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/10 08:09:07
 */
@Component
@Slf4j
public class LoginUserInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserHelp userHelp;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //从cookie中获取token
        String token = CookieUtils.getValue(request);
        //校验token
        if (!ObjectUtils.isEmpty(token)){
            UserToken userToken= (UserToken) redisTemplate.opsForValue().get(
                    String.format(UserConstants.USER_LOGIN_TOKEN,token)
            );
            //判断userToken是否为空
            //判断token是否过期
            if (userToken!=null&&userToken.getTtl().after(new Date())){
                userHelp.set(userToken);
            }else {
                log.debug("token过期或者redis中没有该token");
            }
        }

        return true;
    }
    //视图解析之前

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        //把当前登录的用户信息放在modelAndView中
        UserToken userToken= userHelp.get();
        if (userToken!=null){
            if (modelAndView!=null){
               modelAndView.addObject("loginUser",userToken);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHelp.remove();
    }
}
