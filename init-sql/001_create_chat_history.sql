CREATE TABLE IF NOT EXISTS chat_history (
                                            id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            thread_id  VARCHAR(64) NOT NULL,
    role       ENUM('user','assistant') NOT NULL,
    content    TEXT         NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    );

-- 后期可再加索引
