package com.example.chatgpt_tool.controller;

import com.example.chatgpt_tool.dto.ChatReq;
import com.example.chatgpt_tool.entity.ChatHistory;
import com.example.chatgpt_tool.service.AssistantThreadService;
import com.example.chatgpt_tool.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class chatController {
    @Autowired
    private AssistantThreadService ats;



    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody ChatReq req) {
        return ats.chat(req.userId(), req.message());
    }

    @GetMapping("/history/{userId}")
    public List<ChatHistory> history(@PathVariable Long userId) {
        return ats.listHistory(userId);
    }
}
