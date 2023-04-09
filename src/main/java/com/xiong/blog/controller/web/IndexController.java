package com.xiong.blog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.service.InvitationService;
import com.xiong.blog.service.UserService;
import com.xiong.blog.vo.IndexDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/9 16:59:35
 */
@Controller
public class IndexController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(BlogPage<IndexDataVo> blogPage, ModelMap modelMap){
        //设置帖子一页显示的数量
        blogPage.setSize(5);
        //
       blogPage = (BlogPage<IndexDataVo>) invitationService.indexPage(blogPage);
        blogPage.setPath("/");
        modelMap.put("page",blogPage);
        return "index";
    }
}
