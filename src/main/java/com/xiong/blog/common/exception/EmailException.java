package com.xiong.blog.common.exception;

import lombok.Data;

@Data
public class EmailException extends BlogException {

    public EmailException(String msg){
        super(msg);
    }
}
