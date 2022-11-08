package com.yds.redissondelayedqueue.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:11
 * @Description:
 */
@Component
@Slf4j
public class OrderDelayedListener implements RedisDelayedQueueListener<String> {

    @Override
    public void invoke(String s) {
        log.info("订单到期结束：{}", s);
        throw new RuntimeException("订单到期结束失败");
    }
}
