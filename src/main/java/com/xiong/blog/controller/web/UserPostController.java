package com.xiong.blog.controller.web;

import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.entity.CommentEntity;
import com.xiong.blog.service.CommentService;
import com.xiong.blog.service.InvitationService;
import com.xiong.blog.vo.IndexDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/6 15:57:44
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserPostController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private CommentService commentService;
    @Value("${blog.host}")
    private String host;

    @GetMapping("/discuss/{userId}")
    public String getUserPosts(@PathVariable Integer userId, BlogPage<IndexDataVo> blogPage, ModelMap modelMap) {
        blogPage.setSize(5);
        blogPage = (BlogPage<IndexDataVo>) invitationService.userPostsPage(userId, blogPage);
        modelMap.put("page", blogPage);
        return "site/user-postList";
    }

    @GetMapping("/comment/{userId}")
    public String getUserComment(@PathVariable Integer userId, BlogPage<CommentEntity> blogPage, ModelMap modelMap) {
        blogPage.setSize(5);
        blogPage = commentService.selectUserCommentsByUid(userId, blogPage);
        modelMap.put("page", blogPage);
        return "site/comment";
    }
    @GetMapping("profile/{userId}")
    public String profile(@PathVariable Integer userId){
        return "redirect:"+host+"account/info/"+userId;
    }

}
