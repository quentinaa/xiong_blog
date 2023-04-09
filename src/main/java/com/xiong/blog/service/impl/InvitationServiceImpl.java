package com.xiong.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.common.status.BlogStatus;
import com.xiong.blog.dao.InvitationDao;
import com.xiong.blog.entity.InvitationDataEntity;
import com.xiong.blog.entity.InvitationEntity;
import com.xiong.blog.service.CommentService;
import com.xiong.blog.service.ILikeService;
import com.xiong.blog.service.InvitationService;
import com.xiong.blog.vo.IndexDataVo;
import com.xiong.blog.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/17 08:27:09
 */
@Service("InvitationService")
public class InvitationServiceImpl extends ServiceImpl<InvitationDao, InvitationEntity> implements InvitationService{
    @Autowired
    private ILikeService likeService;
    @Autowired
    private CommentService commentService;
    @Override
    public Page<IndexDataVo> indexPage(BlogPage<IndexDataVo> page) {
        Page<IndexDataVo> indexDataVoPage = baseMapper.selectIndexData(page);


        for (IndexDataVo indexDataVo:page.getRecords()){
            LikeVo likeVo=new LikeVo();
            likeVo.setEntityType(1);
            likeVo.setEntityId(indexDataVo.getInvitationEntity().getId());
            if (indexDataVo.getInvitationDataEntity()==null){
                indexDataVo.setInvitationDataEntity(new InvitationDataEntity());
            }
            Long likeCount = likeService.likeCount(likeVo);
            indexDataVo.getInvitationDataEntity().setLikes(likeCount);
            //存入评论数据条数
            //1.查询帖子对应的评论数量
            Long commentCount = commentService.getCommentCountByTopicId(indexDataVo.getInvitationEntity().getId(), BlogStatus.TOPIC_COMMENT.getCode());
            //2.将查询到的评论数量存入indexDataVo
            indexDataVo.getInvitationDataEntity().setComments(commentCount);
        }
        return indexDataVoPage;
    }

    @Override
    public Page<IndexDataVo> userPostsPage(Integer userId, BlogPage<IndexDataVo> blogPage) {
        Page<IndexDataVo> page = baseMapper.selectUserPostList(userId,blogPage);
        for (IndexDataVo indexDataVo : blogPage.getRecords()) {
            LikeVo likeVo = new LikeVo();
            likeVo.setEntityType(1);
            likeVo.setEntityId(indexDataVo.getInvitationEntity().getId());
            if (indexDataVo.getInvitationDataEntity() == null) {
                indexDataVo.setInvitationDataEntity(new InvitationDataEntity());
            }
            Long likeCount = likeService.likeCount(likeVo);
            indexDataVo.getInvitationDataEntity().setLikes(likeCount);
            //存入评论数据条数
            //查询帖子对应的评论数量
            Long commentCount = commentService.getCommentCountByTopicId(BlogStatus.TOPIC_COMMENT.getCode(),indexDataVo.getInvitationEntity().getId());
            //将查询到的帖子数量存入indexvo
            indexDataVo.getInvitationDataEntity().setComments(commentCount);
        }
        return page;
    }
}
