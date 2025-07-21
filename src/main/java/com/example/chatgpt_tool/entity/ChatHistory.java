package com.example.chatgpt_tool.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ChatHistory {
    private Long id;
    private Long userId;
    private String threadId;
    private String role;        // "user" / "assistant"
    private String content;
    private LocalDateTime createdAt;
    public  ChatHistory(Long id,Long userId ,String threadId, String role, String content, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.threadId = threadId;
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;

    }
}
