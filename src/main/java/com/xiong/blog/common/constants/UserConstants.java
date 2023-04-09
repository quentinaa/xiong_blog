package com.xiong.blog.common.constants;

public interface UserConstants {

    Integer USER_DEFAULE_TIMEOUT = 10 * 24 * 60 * 60; // 默认超时时间是10天，单位是秒

     String ACTIVATEKEY = "blog:user:activate:%s"; // %s占位符

    String USER_LOGIN_TOKEN ="blog:user:token:%s";

    String TOKEN_COOKIE_KEY = "token-cookie-key";

    String USER_INFO_KEY = "blog:user:info:%s";

}
