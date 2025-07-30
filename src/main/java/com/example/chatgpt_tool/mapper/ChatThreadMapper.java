package com.example.chatgpt_tool.mapper;

import com.example.chatgpt_tool.entity.ChatThread;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatThreadMapper {

    @Insert("""
        INSERT INTO chat_thread(thread_id, user_id, title, model)
        VALUES(#{threadId}, #{userId}, #{title}, #{model})
    """)
    void insert(ChatThread t);

    @Select("""
        SELECT * FROM chat_thread
        WHERE user_id = #{userId}
        ORDER BY created_at DESC
    """)
    List<ChatThread> listByUser(Long userId);

    @Select("SELECT * FROM chat_thread WHERE thread_id = #{id}")
    ChatThread find(String id);
}

