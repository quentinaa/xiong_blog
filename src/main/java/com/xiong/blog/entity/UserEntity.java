package com.xiong.blog.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/2/27 15:22:19
 */
@Data
@TableName("t_user")
public class UserEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String pwd;
    private Integer sex;
    private String mobile;
    private String email;
    private Integer statu;
    @TableField(" head_url")
    private String headUrl;
    private String activateCode;
    private Integer score;
    private Date createTime;

}
