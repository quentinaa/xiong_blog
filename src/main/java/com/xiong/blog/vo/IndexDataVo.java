package com.xiong.blog.vo;

import com.xiong.blog.entity.InvitationDataEntity;
import com.xiong.blog.entity.InvitationEntity;
import com.xiong.blog.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiong
 * @version 1.0
 * @description 主页数据接口需要用到
 * @date 2023/3/9 16:41:30
 */
@Data
public class IndexDataVo implements Serializable {
    //作者信息
    private UserEntity userEntity;

    //帖子信息
    private InvitationEntity invitationEntity;

    //帖子数据
    private InvitationDataEntity invitationDataEntity;
}
