package com.xiong.blog.common.utils;

import lombok.Data;

import java.util.HashMap;

// 这个类继承HashMap后变的非常的灵活，可以动态的给添加属性
@Data
public class R extends HashMap {

//    private Integer code; // 响应码(0:成功，1：失败)
//    private String msg; // 响应信息

    public R() {
        super.put("code", 0);
    }

    public static R ok() { // 只是返回成功
        return new R();
    }

    public static R ok(String msg) { // 只是返回成功,有信息
        R ok = ok();
        ok.put("msg", msg);
        return ok;
    }

    public static R error() {
        R r = new R();
        r.put("code", 1);
        return r;
    }

    public static R error(String msg) {
        R error = error();
        error.put("msg", msg);
        return error;
    }

    @Override
    public R put(Object key, Object value) {
        super.put(key, value);
        return this;
    }
}
