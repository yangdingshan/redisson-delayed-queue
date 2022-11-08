package com.yds.redissondelayedqueue.listener;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:13
 * @Description:
 */
@Slf4j
@Component
public class RedisDelayedQueueInit implements ApplicationContextAware {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    private RedisDelayedQueue redisDelayedQueue;

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayedQueueListener> map = applicationContext.getBeansOfType(RedisDelayedQueueListener.class);
        for (Map.Entry<String, RedisDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            String listenerName = taskEventListenerEntry.getValue().getClass().getName();
            startThread(listenerName, taskEventListenerEntry.getValue());
        }
    }

    /**
     * 启动线程获取队列*
     *
     * @param queueName                 queueName
     * @param redisDelayedQueueListener 任务回调监听
     * @param <T>                       泛型
     * @return
     */
    private <T> void startThread(String queueName, RedisDelayedQueueListener redisDelayedQueueListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        //服务重启后，无offer，take不到信息。
        redissonClient.getDelayedQueue(blockingFairQueue);
        //由于此线程需要常驻，可以新建线程，不用交给线程池管理
        Thread thread = new Thread(() -> {
            log.info("启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    log.info("监听队列线程，监听名称：{},内容:{}", queueName, t);
                    redisDelayedQueueListener.invoke(t);
                } catch (Exception e) {
                    log.info("监听队列线程错误,", e);
                    // todo 消费失败，通知管理员
                }
            }
        });
        thread.setName(queueName);
        thread.start();
    }
}
