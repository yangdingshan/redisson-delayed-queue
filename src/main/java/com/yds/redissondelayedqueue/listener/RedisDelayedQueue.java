package com.yds.redissondelayedqueue.listener;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:12
 * @Description:
 */
@Slf4j
@Component
public class RedisDelayedQueue {

    @Autowired
    RedissonClient redissonClient;

    /**
     * 添加队列
     *
     * @param t        DTO传输类
     * @param delay    时间数量
     * @param timeUnit 时间单位
     * @param <T>      泛型
     */
    public  <T> void addQueue(T t, long delay, TimeUnit timeUnit, String queueName) {
        log.info("添加延迟队列,监听名称:{},时间:{},时间单位:{},内容:{}" , queueName, delay, timeUnit,t);
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offer(t, delay, timeUnit);
    }

    public <T> void remove(T t,  String queueName) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.remove(t);
    }

    public <T> void remove(T t, Class<? extends RedisDelayedQueueListener> clazz) {
        remove(t, clazz.getName());
    }

    /**
     * 添加队列-秒
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueSeconds(T t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.SECONDS, clazz.getName());
    }

    /**
     * 添加队列-分
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueMinutes(T t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.MINUTES, clazz.getName());
    }

    /**
     * 添加队列-时
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueHours(T t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.HOURS, clazz.getName());
    }
    /**
     * 添加队列-天
     *
     * @param t     DTO传输类
     * @param delay 时间数量
     * @param <T>   泛型
     */
    public <T> void addQueueDays(T t, long delay, Class<? extends RedisDelayedQueueListener> clazz) {
        addQueue(t, delay, TimeUnit.DAYS, clazz.getName());
    }

}
