package com.example.chatgpt_tool.controller;

import com.example.chatgpt_tool.dto.ChatReq;
import com.example.chatgpt_tool.entity.ChatHistory;
import com.example.chatgpt_tool.service.AssistantThreadService;
import com.example.chatgpt_tool.service.ChatGPTService;
import com.example.chatgpt_tool.service.ChatHistoryService;
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
    @Autowired
    private ChatGPTService chatGPTService;
    @Autowired
    private ChatHistoryService chatHistoryService;



    @PostMapping("/chat")
    public Mono<String> chat(@RequestBody ChatReq req) {
        return ats.chat(req.userId(), req.threadId(),req.message());
    }

    /* ========= （可选）按用户列出所有线程 =========
      @GetMapping("/threads/{userId}")
      public List<ChatThread> threads(@PathVariable Long userId) { … }
   */
    /* ========= 按线程查询历史 ========= */
    @GetMapping("/history/{threadId}")
    public List<ChatHistory> history(@PathVariable String threadId) {
        return chatHistoryService.listByThread(threadId);   // Redis miss → MySQL
    }

}
