package com.xiong.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiong.blog.common.constants.FollowerConstants;
import com.xiong.blog.entity.UserEntity;
import com.xiong.blog.service.impl.FollowerServiceImpl;

/**
 * @author xiong
 * @version 1.0
 * @description
 * @date 2023/3/31 09:20:00
 */
public interface UserInfoService {

     //统计关注者数量
     Long getFollowerCount(Integer userId);
     //统计粉丝数量
     Long getFansCount(Integer userId);
}
