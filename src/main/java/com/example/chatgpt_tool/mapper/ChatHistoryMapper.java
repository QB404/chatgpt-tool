package com.example.chatgpt_tool.mapper;



import com.example.chatgpt_tool.entity.ChatHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper          // 如果已全局 @MapperScan，可省略
public interface ChatHistoryMapper {

    @Insert("""
        INSERT INTO chat_history(user_id,thread_id, role, content)
        VALUES(#{userId},#{threadId}, #{role}, #{content})
    """)
    void insert(ChatHistory msg);

    @Select("""
        SELECT * FROM chat_history
        WHERE thread_id = #{threadId}
        ORDER BY created_at DESC
        LIMIT #{limit}
    """)
    List<ChatHistory> recent(@Param("threadId") String threadId,
                             @Param("limit") int limit);

    /* 查询指定用户全部记录（时间升序） */
    @Select("""
        SELECT * FROM chat_history
        WHERE user_id = #{userId}
        ORDER BY created_at
    """)
    List<ChatHistory> findByUser(@Param("userId") Long userId);
}
