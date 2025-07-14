package com.example.chatgpt_tool.config;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.http.HttpHeaders;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;


@Configuration
public class WebClientConfig {
    @Bean
    public WebClient openAiWebClient() {

        HttpClient httpClient = HttpClient.create().proxy(proxy -> proxy
                        .type(ProxyProvider.Proxy.HTTP)
                        .host("127.0.0.1")
                        .port(7890))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(10));

        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .clientConnector(new ReactorClientHttpConnector(httpClient)) // 关键，注入配置好的 Netty client
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
}}
