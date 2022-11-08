package com.yds.redissondelayedqueue.controller;

import com.yds.redissondelayedqueue.listener.OrderDelayedListener;
import com.yds.redissondelayedqueue.listener.RadioListener;
import com.yds.redissondelayedqueue.listener.RedisDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:15
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    @RequestMapping("/hello")
    public String hello(){
        redisDelayedQueue.addQueueSeconds("订单还有15分钟结束", 10L, RadioListener.class);
        redisDelayedQueue.addQueueSeconds("订单结束", 20L, OrderDelayedListener.class);
        redisDelayedQueue.remove("订单结束", OrderDelayedListener.class);
        return "OK";
    }
}
