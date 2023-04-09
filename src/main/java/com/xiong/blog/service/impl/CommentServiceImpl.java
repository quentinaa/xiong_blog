package com.xiong.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.status.BlogStatus;
import com.xiong.blog.dao.CommentDao;
import com.xiong.blog.entity.CommentEntity;
import com.xiong.blog.service.CommentService;
import com.xiong.blog.service.ILikeService;
import com.xiong.blog.vo.CommentVo;
import com.xiong.blog.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/23 16:13:13
 */
@Service("commentService")
public class CommentServiceImpl
        extends ServiceImpl<CommentDao, CommentEntity>
        implements CommentService {

    @Autowired
    private UserHelp userHelp;
    @Autowired
    private ILikeService likeService;
    @Override
    public BlogPage<CommentVo> getCommentListByTid(BlogPage<CommentVo> page, Integer id) {
        //1.查询一级评论（对帖子评论）
        page=baseMapper.selectCommentListTid(page,id, BlogStatus.TOPIC_COMMENT.getCode());
        BlogPage newReplyPage=new BlogPage();

        //点赞业务
        LikeVo likeVo=new LikeVo();
        likeVo.setEntityType(2);
        if (userHelp.get()!=null){
            likeVo.setUserId(userHelp.get().getUid());
        }
        //查询二级评论（对评论的回复）
        List<CommentVo> records=page.getRecords();
        for (CommentVo comment:records){
            likeVo.setEntityId(comment.getCommentEntity().getId());
            //查询一级评论的点赞信息
            findCommentLikeInfo(comment,likeVo);

            //查询单个一级评论所有回复（查询二级评论）
            Integer cid=comment.getCommentEntity().getId();
            Page<CommentVo> replyPage=baseMapper.selectCommentListTid(newReplyPage,cid,BlogStatus.TOPIC_REPLY.getCode());

            List<CommentVo> records1=replyPage.getRecords();
            for (CommentVo commentVo:records1){
                likeVo.setEntityId(commentVo.getCommentEntity().getId());
                //查看二级评论的点赞数量和状态
                commentVo.setLikeCount(likeService.getUserLikeCount(likeVo.getEntityId()));
                if (likeVo.getUserId()!=null){
                    commentVo.setLikeStatus(likeService.likeStatus(likeVo)?1:0);
                }
            }
            comment.setReplyList(records1);

        }


        return page;
    }

    private void findCommentLikeInfo(CommentVo comment, LikeVo likeVo) {
        comment.setLikeCount(likeService.likeCount(likeVo));
        if (likeVo.getUserId()!=null){
            comment.setLikeStatus(likeService.likeStatus(likeVo)?1:0);
        }
    }

    @Override
    public Long getCommentCountByTopicId(Integer id,Integer type) {
       return baseMapper.getCommentCountByTopicId(id,type);
    }

    @Override
    public BlogPage<CommentEntity> selectUserCommentsByUid(Integer userId, BlogPage<CommentEntity> blogPage) {
        return baseMapper.selectUserCommentList(userId,blogPage);
    }
}

