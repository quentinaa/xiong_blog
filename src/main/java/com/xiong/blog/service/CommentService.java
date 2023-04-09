package com.xiong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.entity.CommentEntity;
import com.xiong.blog.vo.CommentVo;

public interface CommentService extends IService<CommentEntity> {
    // 根据帖子id查询所有评论信息（包括一级评论-评论帖子的  二级评论-评论的回复）
    BlogPage<CommentVo> getCommentListByTid(BlogPage<CommentVo> page,Integer id);
    Long getCommentCountByTopicId(Integer id,Integer type);

    BlogPage<CommentEntity> selectUserCommentsByUid(Integer userId, BlogPage<CommentEntity> blogPage);
}
