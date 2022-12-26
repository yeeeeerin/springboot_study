package com.example.stock.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisRepository {
    private RedisTemplate<String,String> template;

    public RedisRepository(RedisTemplate<String, String> template) {
        this.template = template;
    }

    public Boolean lock(Long key){
        return template
                .opsForValue()
                .setIfAbsent(generateKey(key),"lock", Duration.ofMillis(3_000));
    }

    public Boolean unlock(Long key){
        return template.delete(generateKey(key));
    }

    private String generateKey(Long key) {
        return key.toString();
    }
}
