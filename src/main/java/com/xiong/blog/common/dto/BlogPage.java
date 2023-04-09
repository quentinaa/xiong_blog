package com.xiong.blog.common.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class BlogPage<T> extends Page<T> {

    private String path;

    // 开始
    public long getFrom() {
        long index = super.getCurrent() - 2;
        if (index <= 0) {
            return 1;
        }
        return index;
    }

    // 结束
    public long getTo() {
        long index = super.getCurrent() + 2;
        if (index >= super.getPages()) {
            return super.getPages();
        }
        return index;
    }
}
