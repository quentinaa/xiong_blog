package com.xiong.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {
        String version = SpringBootVersion.getVersion();
        System.out.println(version);
    }

}
