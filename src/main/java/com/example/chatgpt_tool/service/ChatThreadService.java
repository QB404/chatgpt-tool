package com.example.chatgpt_tool.service;

import com.example.chatgpt_tool.entity.ChatThread;
import com.example.chatgpt_tool.mapper.ChatThreadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatThreadService {

    @Autowired
    private ChatThreadMapper chatThreadMapper;

    /**
     * 获取某个用户的全部对话线程
     * @param userId 用户 ID
     * @return 该用户的对话列表，按创建时间降序
     */
    public List<ChatThread> getThreadsByUser(Long userId) {
        return chatThreadMapper.listByUser(userId);
    }

    /**
     * 获取单个对话线程
     * @param threadId 线程 ID
     * @return 对话详情
     */
    public ChatThread findById(String threadId) {
        return chatThreadMapper.find(threadId);
    }
}

