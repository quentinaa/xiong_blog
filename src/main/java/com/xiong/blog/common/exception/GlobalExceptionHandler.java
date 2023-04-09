package com.xiong.blog.common.exception;

import com.xiong.blog.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 业务异常，由开发人员明确抛出的异常
    @ExceptionHandler(BlogException.class) // 如果抛出的是BlogException异常的或者该异常的子类就会进入到这个方法进行异常处理
    public R businessException(BlogException e) {
        log.error("业务异常", e);
        return R.error(e.getMsg());
    }


    // 系统异常，就是系统的bug
    @ExceptionHandler(Exception.class) // 如果抛出的是BlogException异常的或者该异常的子类就会进入到这个方法进行异常处理
    public R systemException(Exception e) {
        log.error("系统异常", e);
        return R.error("系统正在维护，请联系管理员。。。");
    }
}
