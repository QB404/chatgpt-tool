package com.example.chatgpt_tool.cache;

public interface ThreadCache {
    String get(Long userId);
    void put(Long userId, String threadId);
}
