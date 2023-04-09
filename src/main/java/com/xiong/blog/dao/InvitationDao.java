package com.xiong.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.blog.entity.InvitationEntity;
import com.xiong.blog.vo.IndexDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvitationDao extends BaseMapper<InvitationEntity> {
    Page<IndexDataVo> selectIndexData(Page<IndexDataVo> page);
    Page<IndexDataVo> selectUserPostList(@Param("userId") Integer userId, Page<IndexDataVo> blogPage);
}
