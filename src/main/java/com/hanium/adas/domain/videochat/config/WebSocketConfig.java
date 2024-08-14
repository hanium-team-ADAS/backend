package com.hanium.adas.domain.videochat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws")
//                .setAllowedOrigins("*");
//
////                .withSockJS();
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalHandler(), "/signal")
                .setAllowedOrigins("http://localhost:3000")
                .setAllowedOrigins("http://react-front-telehealth.s3-website.ap-northeast-2.amazonaws.com")
                .setAllowedOrigins("*");

//                .withSockJS(); // allow all origins
    }

    @Bean
    public WebSocketHandler signalHandler() {
        return new SignalHandler();
    }
}