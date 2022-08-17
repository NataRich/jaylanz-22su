package com.jaylanz.framework.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
    @Bean
    public RedisCustomConversions redisCustomConversions(BytesToTimestampConverter bytesToTimestampConverter) {
        return new RedisCustomConversions(List.of(bytesToTimestampConverter));
    }
}
