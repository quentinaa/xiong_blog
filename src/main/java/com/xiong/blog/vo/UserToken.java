package com.xiong.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/6 14:13:16
 */
//方便数据转换Serializable
@Data
public class UserToken implements Serializable {
    private Integer uid;//用户id
    private String uname;//用户名字
    private Date ttl;//过期时间
    private Date createTime;//登录时间
    private String token;//令牌
    private String headerUrl;//用户头像
}
