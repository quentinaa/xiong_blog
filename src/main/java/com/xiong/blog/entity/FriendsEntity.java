package com.xiong.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/5 12:34:49
 */
@Data
@TableName("t_user_friends")
public class FriendsEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer userFriendsId;
    private String  userNote;
    private Integer userStatus;
    private Date addTime;
}
