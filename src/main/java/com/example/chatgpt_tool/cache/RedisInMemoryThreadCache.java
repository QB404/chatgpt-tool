package com.example.chatgpt_tool.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisInMemoryThreadCache implements ThreadCache {
    @Autowired
    private StringRedisTemplate redis;
    private static final String KEY_PREFIX = "thread:user:";   // userId → threadId

    @Override
    public String get(Long userId) {
        return redis.opsForValue().get(KEY_PREFIX + userId);
    }

    @Override
    public void put(Long userId, String threadId) {
        redis.opsForValue().set(KEY_PREFIX + userId, threadId);
    }
}
