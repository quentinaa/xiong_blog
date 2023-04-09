package com.xiong.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_invitation_data")
public class InvitationDataEntity {
    private Integer id;
    private Integer tid;//帖子id
    private Integer pv;//浏览量
    private Long likes;//点赞量

    private Long comments;//评论量
    private Integer collect;//收藏量
}
