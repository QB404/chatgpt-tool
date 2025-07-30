package com.example.chatgpt_tool.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* Thread 实体 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatThread {
    private String threadId;
    private Long   userId;
    private String title;      // 可为空
    private String model;      // 可为空
    private LocalDateTime createdAt;
}

