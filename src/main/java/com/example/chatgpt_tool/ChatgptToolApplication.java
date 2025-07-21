package com.example.chatgpt_tool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secret.properties")
@MapperScan("com.example.chatgpt_tool.mapper")
public class ChatgptToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatgptToolApplication.class, args);
	}

}
