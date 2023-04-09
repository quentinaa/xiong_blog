package com.xiong.blog.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xiong.blog.entity.UserEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/5 15:32:05
 */
@Data
public class FriendsVo extends UserEntity{
    private Integer userId;
    private Integer userFriendsId;
    private String  userNote;
    private Integer userStatus;
    private Date addTime;
}
