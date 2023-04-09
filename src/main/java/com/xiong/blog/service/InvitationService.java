package com.xiong.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.dao.InvitationDao;
import com.xiong.blog.entity.InvitationEntity;
import com.xiong.blog.vo.IndexDataVo;

public interface InvitationService extends IService<InvitationEntity> {
    Page<IndexDataVo> indexPage(BlogPage<IndexDataVo> page);

    //我的帖子
    Page<IndexDataVo> userPostsPage(Integer userId, BlogPage<IndexDataVo> page);
}
