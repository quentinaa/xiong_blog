package com.xiong.blog.controller.web;

import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.entity.CommentEntity;
import com.xiong.blog.service.CommentService;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/23 16:42:44
 */
@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserHelp userHelp;



    @Value("${blog.host}")
    private String host;
    @PostMapping("/add/{topicId}")
    public String add(@PathVariable Integer topicId, CommentEntity commentEntity){
        //插入评论信息
        //获取用户id
        UserToken userToken = userHelp.get();
        //判断用户信息是否为空
        if (userToken==null){
            throw new BlogException("用户没登录");
        }
        commentEntity.setStatu(1);
        commentEntity.setCreateTime(new Date());
        commentEntity.setUid(userToken.getUid());
        //向帖子表插入数据
        commentService.save(commentEntity);
        return "redirect:"+host+"topic/detail/"+topicId;
    }
}
