package com.xiong.blog.common.help;

import com.xiong.blog.vo.UserToken;
import org.springframework.stereotype.Component;

@Component
public class UserHelp {

    private ThreadLocal<UserToken> threadLocal = new ThreadLocal<>();

    public void set(UserToken userToken) {
        threadLocal.set(userToken);
    }

    public UserToken get() {
        return threadLocal.get();
    }

    public void remove(){
        threadLocal.remove();
    }
}
