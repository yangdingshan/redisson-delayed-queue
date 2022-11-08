package com.yds.redissondelayedqueue.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yangdingshan
 * @Date: 2022/11/8 11:03
 * @Description:
 */
@Configuration
public class RedisQueueConfig {

    // 连接redis的地址
    @Value("${spring.redis.host}")
    private String host;

    //redis的端口号
    @Value("${spring.redis.port}")
    private String port;

    //redis的密码
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient(){
        //此处为单机配置，高可用配置请往下看
        Config config = new Config();
        if (password.equals("")){
            config.setCodec(new org.redisson.client.codec.StringCodec());
            config.useSingleServer().setAddress("redis://" + host + ":" + port).setDatabase(2).setTimeout(5000);
        }else {
            config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password).setDatabase(2).setTimeout(5000);
        }

        return Redisson.create(config);
    }
}
