package com.example.chatgpt_tool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class ChatGPTService {


    private final WebClient webClient;

    public ChatGPTService(WebClient openAiWebClient) {
        this.webClient = openAiWebClient;
    }

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    /*
        @Autowired
        private RestTemplate restTelate;
    /*
        public String chat(String userMessage) {
            // 构造请求体
            Map<String, Object> message = Map.of(
                    "role", "user",
                    "content", userMessage
            );
            Map<String, Object> body = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(message)
            );

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // 请求封装
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            // 发请求
            ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, entity, Map.class);

            // 解析返回值
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            Map<String, Object> first = (Map<String, Object>) choices.get(0).get("message");

            return first.get("content").toString().trim();


        }
    */
    public Mono<String> chat(String userMessage) {
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "user", "content", userMessage)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .headers(headers -> headers.setBearerAuth(apiKey))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return message.get("content").toString();
                });
    }
}
