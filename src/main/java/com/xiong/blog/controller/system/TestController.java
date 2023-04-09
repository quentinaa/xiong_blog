package com.xiong.blog.controller.system;

//import com.qf.blog.common.help.UserHelp;
//import com.qf.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@EnableAsync
@Async
public class TestController {

//    @Autowired
//    private UserHelp userHelp;

    @Autowired
    private RedisTemplate redisTemplate;
    private Map<String, Long> map = new ConcurrentHashMap<>();
    private Set<Long> set = new HashSet<>();

    @GetMapping("/testIncr")
    @ResponseBody
    public Long testIncr() {
        Long increment = redisTemplate.opsForValue().increment("test-incr");
        long id = Thread.currentThread().getId();
        map.put(increment + "", id);
        if (!set.add(increment)) {
            System.out.println("重复:线程id:" + id + "，值" + increment);
            Long tId = map.get(increment + "");
            System.out.println("被重复:线程Id:" + tId + ",值:" + increment);
        }
//        System.out.println(set.size());
        return increment;
    }

    @GetMapping("/testSet")
    @ResponseBody
    public String testSet() {
        int size = map.size();
        System.out.println(size);
        return map +"";
    }

//    @GetMapping("/getLoginUser1")
//    @ResponseBody
//    public UserToken getLoginUser1() {
//        UserToken userToken = userHelp.get();
//        return userToken;
//    }

    public String getLoginUser2() {
        return null;
    }

    @GetMapping("/testThread")
    public String testThread() {
        System.out.println("TestController.testThread:" + Thread.currentThread().getId());
        service(); // 模拟调用service
        return "testThread";
    }

    private void service() {
        System.out.println("TestController.service:" + Thread.currentThread().getId());
        mapper();
    }

    private void mapper() {
        System.out.println("TestController.mapper:" + Thread.currentThread().getId());
    }


    // 浏览器访问:http://localhost:8001/site/register.html
    // 访问视图都通过这一个Controller完成
    @GetMapping("/{path}/{page}.html")
    public String forward(@PathVariable String path, @PathVariable String page) {
        return path + "/" + page; // classpath:/site/register.html
    }


    @GetMapping("/test")
    public String test(ModelMap modelMap) {
        modelMap.addAttribute("msg", "HelloWolrd");
        // 视图解析
        //[classpath:/templates/]demo[.html]
        return "demo"; // demo是一个视图名称,此时就会转发demo视图
    }
}
