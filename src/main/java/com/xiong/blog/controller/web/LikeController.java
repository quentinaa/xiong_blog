package com.xiong.blog.controller.web;

import com.xiong.blog.common.exception.BlogException;
import com.xiong.blog.common.help.UserHelp;
import com.xiong.blog.common.utils.R;
import com.xiong.blog.service.ILikeService;
import com.xiong.blog.vo.LikeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/27 14:43:35
 */
@Slf4j
@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private UserHelp userHelp;
    @PostMapping("/clickLike")
    public R clickLike(@RequestBody LikeVo likeVo){
        if (userHelp.get()==null){
            throw new BlogException("用户未登录");
        }
        likeVo.setUserId(userHelp.get().getUid());
        //点赞业务
        iLikeService.clickLike(likeVo);
        //查询 帖子/评论点赞的数据
        Long likeCount= iLikeService.likeCount(likeVo);
        //查询 帖子/评论 点赞状态（你是否进行过点赞）
        Boolean likeStatus = iLikeService.likeStatus(likeVo);
        return R.ok().put("likeCount",likeCount).put("likeStatus",likeStatus?1:0);
    }

}
