package com.xiong.blog.service.impl;

import com.xiong.blog.common.constants.FollowerConstants;
import com.xiong.blog.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/4/3 23:57:59
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Long getFollowerCount(Integer userId) {
        String key = String.format(FollowerConstants.FOLLOWER_KEY, userId);
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public Long getFansCount(Integer userId) {
        String key = String.format(FollowerConstants.FANS_KEY, userId);
        return redisTemplate.opsForHash().size(key);
    }
}
