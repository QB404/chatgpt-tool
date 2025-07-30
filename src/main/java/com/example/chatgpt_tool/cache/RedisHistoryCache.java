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
public class RedisHistoryCache {

    @Autowired
    private StringRedisTemplate redis;
    private static final String KEY_PRE = "history:thread:";   // ← 新前缀

    private static final ObjectMapper MAPPER =
            new ObjectMapper().findAndRegisterModules();

    public void append(ChatHistory h) {
        String key = KEY_PRE + h.getThreadId();
        redis.opsForList().rightPush(key, toJson(h));
        redis.opsForList().trim(key, -100, -1);           // 只留最近 100
        redis.expire(key, Duration.ofDays(3));
    }

    /* 拉取整个线程的缓存（miss 时返回 null） */
    public List<ChatHistory> list(String threadId) {

        List<String> raw = redis.opsForList()
                .range(KEY_PRE + threadId, 0, -1);

        if (raw == null || raw.isEmpty()) return null;
        return raw.stream().map(this::fromJson).toList();
    }

    /* ---- JSON util ---- */
    private String toJson(ChatHistory h) {
        try { return MAPPER.writeValueAsString(h); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
    private ChatHistory fromJson(String s) {
        try { return MAPPER.readValue(s, ChatHistory.class); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}

