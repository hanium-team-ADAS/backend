package com.hanium.adas.domain.temperature.application;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "aws.iot")
@Configuration("mqttConfigCustom")
public class MqttConfig {
    private String clientEndpoint;
    private String clientId;
    private String accessKeyId;
    private String secretAccessKey;
}