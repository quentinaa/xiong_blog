package com.xiong.blog.vo;

import lombok.Data;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/16 16:58:02
 */
//点赞Vo
@Data
public class LikeVo {
    //点赞 可以对帖子点赞(1) entityType==1 entityId是帖子id
    // 可以对评论点赞（2） entityType==2  entityId是评论id

    private Integer entityType;
    private Integer entityId;
    //被点赞的用户id
    private Integer entityUserId;
    private Integer userId;
}
