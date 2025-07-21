package com.example.chatgpt_tool.service;


import com.example.chatgpt_tool.cache.InMemoryThreadCache;
import com.example.chatgpt_tool.cache.RedisInMemoryThreadCache;
import com.example.chatgpt_tool.cache.ThreadCache;
import com.example.chatgpt_tool.entity.ChatHistory;
import com.example.chatgpt_tool.mapper.ChatHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service

public class AssistantThreadService {

    @Autowired
    private WebClient openAi;
    // 已配置好代理的 WebClient
    @Autowired
    private ThreadCache cache;

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.assistant-id}")
    private String assistantId;

    /** 对外主业务：传入 userId 与用户消息，返回 GPT 回复 */
    public Mono<String> chat(Long userId, String userMsg) {

        // ① 取或建 threadId
        String threadId = cache.get(userId);
        if (threadId == null) {
            threadId = createThread();
            cache.put(userId, threadId);
        }

        // ② 写入用户 Message
        openAi.post()
                .uri("/threads/{id}/messages", threadId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(Map.of("role", "user", "content", userMsg))
                .retrieve().toBodilessEntity().block();
        chatHistoryMapper.insert(new ChatHistory(null,userId,threadId,"user", userMsg,LocalDateTime.now()));

        // ③ 触发 Run
        String runId = openAi.post()
                .uri("/threads/{id}/runs", threadId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(Map.of("assistant_id", assistantId))
                .retrieve()
                .bodyToMono(Map.class)
                .map(m -> (String) m.get("id"))
                .block();

        // ④ 轮询（简单阻塞版）
        waitRunCompleted(threadId, runId);

        // ⑤ 取最后一条 Assistant 回复
        return fetchLatestAnswer(threadId,userId);
    }

    /** 查询 */
    public List<ChatHistory> listHistory(Long userId) {
        return chatHistoryMapper.findByUser(userId);
    }


    /* ---------- 私有工具方法 ---------- */

    private String createThread() {
        return openAi.post()
                .uri("/threads")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .map(m -> (String) m.get("id"))
                .block();
    }

    private void waitRunCompleted(String threadId, String runId) {
        while (true) {
            Map run = openAi.get()
                    .uri("/threads/{tid}/runs/{rid}", threadId, runId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            if ("completed".equals(run.get("status"))) break;
            try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        }
    }

    private Mono<String> fetchLatestAnswer(String threadId,Long userId) {
        return openAi.get()
                .uri("/threads/{id}/messages?order=desc&limit=1", threadId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .map(resp -> {
                    // data 是 List
                    List<Map<String, Object>> data = (List<Map<String, Object>>) resp.get("data");
                    Map<String, Object> msg0   = data.get(0);

                    // content 也是 List
                    List<Map<String, Object>> contents = (List<Map<String, Object>>) msg0.get("content");
                    Map<String, Object> content0 = contents.get(0);

                    // text 对象中取 value
                    Map<String, Object> textObj = (Map<String, Object>) content0.get("text");


                    return textObj.get("value").toString().trim();
                })
                .doOnNext(reply -> chatHistoryMapper.insert(new ChatHistory(null,userId,threadId,"assistant",reply,LocalDateTime.now())))
                ;
    }

}
