package com.xiong.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 帖子实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_invitation")
public class InvitationEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;//主键
    private String title;//帖子标题
    private String content; // 内容
    private Integer statu;//状态
    private Integer uid;//
    private Date createTime;
    private Double score;
}
