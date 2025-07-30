package com.example.chatgpt_tool.service;
import com.example.chatgpt_tool.cache.RedisHistoryCache;
import com.example.chatgpt_tool.cache.RedisThreadCache;
import com.example.chatgpt_tool.entity.ChatHistory;
import com.example.chatgpt_tool.mapper.ChatHistoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatHistoryService {


    private final ChatHistoryMapper mapper;
    private final RedisHistoryCache cache;

    /*
    public List<ChatHistory> listByUser(Long userId) {
        // ① 先查缓存
        List<ChatHistory> list = cache.list(userId);
        if (list != null) return list;

        // ② 走数据库 + 回填
        list = mapper.findByUser(userId);
        list.forEach(cache::append);
        return list;
    }
    */
    /** 查询某个 thread 的全部 / 最近 N 条消息 */
    public List<ChatHistory> listByThread(String threadId) {

        // 1️⃣ 先查 Redis（最多 100 条的热数据）
        List<ChatHistory> list = cache.list(threadId);
        if (list != null) return list;

        // 2️⃣ 未命中 → 查 MySQL 再写回缓存
        list = mapper.findByThread(threadId);
        list.forEach(cache::append);
        return list;
    }


    /* 保存消息时调用 */
    public void save(ChatHistory h) {
        mapper.insert(h);
        cache.append(h);
    }
}
