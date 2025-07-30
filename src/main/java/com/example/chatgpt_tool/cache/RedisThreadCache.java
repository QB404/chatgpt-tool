package com.example.chatgpt_tool.cache;

import com.example.chatgpt_tool.entity.ChatHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RedisThreadCache implements ThreadCache {
    @Autowired
    private StringRedisTemplate redis;
    private static final String KEY_PREFIX = "thread:user:";   // userId → threadId
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .findAndRegisterModules(); // 序列化 LocalDateTime

    @Override
    public String get(Long userId) {
        return redis.opsForValue().get(KEY_PREFIX + userId);
    }

    @Override
    public void put(Long userId, String threadId) {
        redis.opsForValue().set(KEY_PREFIX + userId, threadId);
    }


}
