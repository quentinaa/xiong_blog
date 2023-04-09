package com.xiong.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/1 16:15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String title; //邮件标题
    private String content;//邮件内容
    private String toUser;//收件人
}
