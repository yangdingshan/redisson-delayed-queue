package com.yds.redissondelayedqueue.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:09
 * @Description:
 */
@Slf4j
@Component
public class RadioListener implements RedisDelayedQueueListener<String> {

    @Override
    public void invoke(String s) {
        log.info("订单倒计时通知：{}", s);
    }
}
