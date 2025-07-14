package com.example.chatgpt_tool.controller;

import com.example.chatgpt_tool.service.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class chatController {


    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping
    public Mono<String> chat(@RequestBody Map<String, String> request) {
        String userInput = request.get("message");
        return chatGPTService.chat(userInput);
    }
}
