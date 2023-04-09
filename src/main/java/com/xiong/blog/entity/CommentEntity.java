package com.xiong.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_comment")
public class CommentEntity {
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;//主键
    private Integer uid;//
    private Integer entityType;
    private Integer entityId;
    private Integer targetId;
    private String content;
    private Integer statu;
    private Date createTime;
}
