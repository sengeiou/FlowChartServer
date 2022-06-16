package com.flowchart.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @className: RedisTemplateUtil
 * @Description: TODO
 * @author: ct
 * @date: 2022/6/16 11:28
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisTemplateUtil {

    @Resource
    public RedisTemplate redisTemplate;


    /**
     * Redis集合（set)
     */
    public void sAdd(final String key, final String... value){
        redisTemplate.opsForSet().add(key,value);
    }


}
