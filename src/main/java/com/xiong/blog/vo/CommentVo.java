package com.xiong.blog.vo;


import com.xiong.blog.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/20 14:24:39
 */
@Data
public class CommentVo {
    private UserEntity user; //评论的人
    private CommentInfoVo commentEntity;//评论的信息
    private List<CommentVo> replyList;//评论回复的集合
    private Long likeCount;//这个评论点赞数量
    private Integer likeStatus=0;//当前登录用户对这个评论的点赞状态
}
