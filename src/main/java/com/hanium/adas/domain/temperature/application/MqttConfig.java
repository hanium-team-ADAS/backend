package com.hanium.adas.domain.temperature.application;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.iot")
public class MqttConfig {
    private String clientEndpoint;
    private String clientId;
    private String accessKeyId;
    private String secretAccessKey;
}