package com.xiong.blog.service.impl;

import com.xiong.blog.common.constants.LikeConstatns;
import com.xiong.blog.service.ILikeService;
import com.xiong.blog.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/27 14:10:20
 */
@Service
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void clickLike(LikeVo likeVo) {
        //点赞业务 数据的一致性 事务（A-520  B+520） 回滚
        //mysql redis 最大连接数
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //对帖子/评论点赞
                String key=String.format(LikeConstatns.LIKE_TOPIC_KEY,likeVo.getEntityType(),likeVo.getEntityId());
                //对人点赞
                String userLikeKey=String.format(LikeConstatns.USER_LIKE_KEY,likeVo.getEntityUserId());
                //判断是否对这个帖子/评论 进行过点赞
                Boolean member=redisTemplate.opsForSet().isMember(key,likeVo.getUserId());
                //开启事务
                operations.multi();
                if (member){
                    Long remove = redisTemplate.opsForSet().remove(key, likeVo.getUserId());
                    redisTemplate.opsForValue().decrement(userLikeKey);
                }else {
                    Long add = redisTemplate.opsForSet().add(key, likeVo.getUserId());
                    redisTemplate.opsForValue().increment(userLikeKey);
                }
                //提交事务
                operations.exec();
                return null;
            }
        });
    }
    @Override
    public Long likeCount(LikeVo likeVo) {
        //统计 帖子/评论 的总点赞数点赞
        String key=String.format(LikeConstatns.LIKE_TOPIC_KEY,likeVo.getEntityType(),likeVo.getEntityId());
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Long getUserLikeCount(Integer userId) {
        //统计用户的总点赞数据
        String userLikeKey=String.format(LikeConstatns.USER_LIKE_KEY,userId);
        Object o = redisTemplate.opsForValue().get(userLikeKey);
        if (o==null){
            return 0L;
        }
        return Long.parseLong(o.toString());
    }

    @Override
    public Boolean likeStatus(LikeVo likeVo) {
        //查看 是否 对帖子/评论进行过点赞
        String key=String.format(LikeConstatns.LIKE_TOPIC_KEY,likeVo.getEntityType(),likeVo.getEntityId());
        return redisTemplate.opsForSet().isMember(key,likeVo.getUserId());
    }
}
