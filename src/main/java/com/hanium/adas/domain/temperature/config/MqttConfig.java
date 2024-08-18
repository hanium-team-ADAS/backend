package com.hanium.adas.domain.temperature.config;

import com.amazonaws.services.iot.client.AWSIotMqttClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.SecureRandom;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.iot")
public class MqttConfig {
    private String clientEndpoint;
    private String clientId;
    private String accessKeyId;
    private String secretAccessKey;

    @Bean
    public AWSIotMqttClient mqttClient() {
        // CLient ID 동적 생성
        AWSIotMqttClient mqttClient = new AWSIotMqttClient(clientEndpoint, clientId  + new BigInteger(64, new SecureRandom()).toString(32), accessKeyId, secretAccessKey);
        mqttClient.setConnectionTimeout(30);
        mqttClient.setServerAckTimeout(10);
        mqttClient.setKeepAliveInterval(0);
        return mqttClient;
    }
}