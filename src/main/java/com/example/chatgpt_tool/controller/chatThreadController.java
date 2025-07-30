package com.example.chatgpt_tool.controller;

import com.example.chatgpt_tool.entity.ChatThread;
import com.example.chatgpt_tool.service.ChatThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/threads")
public class chatThreadController {

    @Autowired
    private ChatThreadService threadService;

    @GetMapping("/user/{userId}")
    public List<ChatThread> listThreads(@PathVariable Long userId) {
        return threadService.getThreadsByUser(userId);
    }
}

