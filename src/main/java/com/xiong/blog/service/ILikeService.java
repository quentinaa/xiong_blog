package com.xiong.blog.service;

import com.xiong.blog.vo.LikeVo;

public interface ILikeService {
    //点赞
    void clickLike(LikeVo likeVo);

    //统计（帖子|评论）点赞数量
    Long likeCount(LikeVo likeVo);

    Long getUserLikeCount(Integer userId);

     Boolean likeStatus(LikeVo likeVo);
}
