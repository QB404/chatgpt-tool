package com.example.chatgpt_tool.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class testController {

    @PostMapping
    public String test(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        return "said "+message;
    }
}
