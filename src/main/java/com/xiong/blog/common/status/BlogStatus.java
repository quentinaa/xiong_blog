package com.xiong.blog.common.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

// 把枚举类当成常量类使用
@AllArgsConstructor
@NoArgsConstructor
public enum BlogStatus {

    USER_ACTIVATE(1, "用户已激活"), // 用户已激活
    USER_UN_ACTIVATE(0, "用户未激活"), // 用户未激活

    TOPIC_COMMENT(1, "帖子"), // 给帖子评论

    TOPIC_REPLY(2, "回复"); // 给评论回复

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;


}
