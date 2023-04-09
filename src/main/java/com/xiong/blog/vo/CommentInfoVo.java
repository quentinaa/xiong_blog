package com.xiong.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/20 14:18:33
 */
//评论类
@Data
public class CommentInfoVo {
    private Integer id;
    private Integer uid;//用户id
    private Integer entityType;//可以对帖子进行评论 对评论进行评论
    private Integer entityId;
    private Integer targetId;//目标id
    private String targetName;//目标名字
    private String content;//评论内容
    private Integer statu;//评论状态
    private Date createTime;//创建时间
}
