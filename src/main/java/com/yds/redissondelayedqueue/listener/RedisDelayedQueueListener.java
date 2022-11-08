package com.yds.redissondelayedqueue.listener;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:06
 * @Description:
 */
public interface RedisDelayedQueueListener<T> {

    /**
     * 执行方法
     *
     * @param t
     */
    void invoke(T t);

}
