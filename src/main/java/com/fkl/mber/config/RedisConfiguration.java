/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author jeongsik.lee
 *
 */
@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
	
	@Autowired ClusterConfigurationProperties clusterConfigurationProperties;
    
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {

        // clustering  구성 config
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        clusterConfigurationProperties.getNodes().forEach(s -> {
            String[] url = s.split(":");
            redisClusterConfiguration.clusterNode(url[0],Integer.parseInt(url[1]));
        });

        return new LettuceConnectionFactory(redisClusterConfiguration);
    }
	
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
 
}