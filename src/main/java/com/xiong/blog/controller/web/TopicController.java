package com.xiong.blog.controller.web;

import com.xiong.blog.common.dto.BlogPage;
import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.entity.InvitationEntity;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.CommentService;
import com.xiong.blog.service.ILikeService;
import com.xiong.blog.service.InvitationService;
import com.xiong.blog.service.UserService;
import com.xiong.blog.vo.CommentVo;
import com.xiong.blog.vo.LikeVo;
import com.xiong.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/20 14:30:56
 */
@Slf4j
@Controller
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private UserHelp userHelp;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ILikeService likeService;

    @PostMapping("/publish")
    @ResponseBody
    public R publish(@RequestBody InvitationEntity invitationEntity){
        //获取用户id
        UserToken userToken = userHelp.get();
        //判断用户信息是否为空
        if (userToken==null){
            throw new BlogException("用户没登录");
        }
        invitationEntity.setStatu(1);
        invitationEntity.setCreateTime(new Date());
        invitationEntity.setUid(userToken.getUid());
        //向帖子表插入数据
        invitationService.save(invitationEntity);
        return R.ok();
    }
    @GetMapping("/detail/{id}")
    public String detail(BlogPage<CommentVo> page, @PathVariable Integer id,
                         ModelMap modelMap){
        //1.查询帖子信息
        InvitationEntity topic= invitationService.getById(id);
        //2.查询作者信息
        UserEntity userEntity = userService.getById(topic.getUid());
        page.setSize(5);
        //查询帖子对应的评论信息
        page=commentService.getCommentListByTid(page,id);
        //点赞信息
        LikeVo likeVo=new LikeVo();
        likeVo.setEntityId(id);
        likeVo.setEntityType(1);
        if (userHelp.get()!=null){
            //如果用户已经登录
            likeVo.setUserId(userHelp.get().getUid());
        }
        //统计当前帖子的点赞数量和
        Long likeCount = likeService.likeCount(likeVo);
        //查看当前用户对帖子的点赞状态
        Boolean likeStatus = likeService.likeStatus(likeVo);
        page.setPath("/topic/detail/"+id);

        modelMap.addAttribute("topic",topic);
        modelMap.addAttribute("user",userEntity);
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("likeCount",likeCount);
        modelMap.addAttribute("likeStatus",likeStatus);
        return "site/discuss-detail";
    }
    
}
