package com.example.chatgpt_tool.cache;



import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * userId  →  threadId 映射
 * 仅存内存，应用重启即失效。
 */
@Profile("local")
@Component
public class InMemoryThreadCache implements ThreadCache{
    private final Map<Long, String> map = new ConcurrentHashMap<>();

    public String get(Long userId) {
        return map.get(userId);
    }

    public void put(Long userId, String threadId) {
        map.put(userId, threadId);
    }
}
