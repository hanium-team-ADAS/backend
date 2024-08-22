package com.hanium.adas.domain.videochat.config;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.server.WsSci;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalHandler(), "/signal")
                .setAllowedOrigins("http://localhost:3000",
                        "http://react-front-telehealth.s3-website.ap-northeast-2.amazonaws.com")
                .setAllowedOrigins("*"); // allow all origins
    }

    @Bean
    public WebSocketHandler signalHandler() {
        return new SignalHandler();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(connector -> {
            connector.setMaxPostSize(20971520); // 20MB, POST 요청의 최대 크기
            connector.setProperty("maxTextMessageBufferSize", String.valueOf(10485760)); // 10MB
            connector.setProperty("maxBinaryMessageBufferSize", String.valueOf(10485760)); // 10MB
        });

        // WebSocket 설정 추가
        tomcat.getTomcatContextCustomizers().add(context -> {
            context.addServletContainerInitializer(new WsSci(), null);
            context.setSessionTimeout(30);
        });

        return tomcat;
    }
}
