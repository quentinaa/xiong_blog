package com.xiong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.entity.CommentEntity;
import com.xiong.blog.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/22 16:28:45
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
    //查询评论接口
    BlogPage<CommentVo> selectCommentListTid(
            @Param("page") BlogPage<CommentVo> page,
            @Param("tid") Integer id,
            @Param("type") Integer type
    );
    Long getCommentCountByTopicId(@Param("id") Integer id,
                                  @Param("type") Integer type);

    BlogPage<CommentEntity> selectUserCommentList(Integer userId, BlogPage<CommentEntity> blogPage);
}
