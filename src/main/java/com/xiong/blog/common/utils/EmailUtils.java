package com.xiong.blog.common.utils;

import com.xiong.blog.dto.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/1 16:07:32
 */
@Component
public class EmailUtils {

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromUser;

    public void sendEmail(Email email) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {

            // 封装邮件的信息
            helper.setSubject(email.getTitle());
            helper.setText(email.getContent(), true); // true代表的是解析HTML标签
            helper.setTo(email.getToUser()); //收件人
            helper.setFrom(fromUser); // 发件人

            // 发送邮件
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
