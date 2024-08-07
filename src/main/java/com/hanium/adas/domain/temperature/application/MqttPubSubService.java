package com.hanium.adas.domain.temperature.application;

import com.amazonaws.services.iot.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MqttPubSubService {

    private AWSIotMqttClient mqttClient;

    // 발행, 구독을 위한 값
    static final private String subTopic = "pic";
    static final private String pubTopic = "snap";

    @Autowired
    private MqttConfig mqttConfig;

    public void snap() {
        // mqttClient 설정
        mqttClient = new AWSIotMqttClient(
                mqttConfig.getClientEndpoint(),
                mqttConfig.getClientId(),
                mqttConfig.getAccessKeyId(),
                mqttConfig.getSecretAccessKey()
        );
        mqttClient.setConnectionTimeout(30);
        mqttClient.setServerAckTimeout(10);

        String payload = "{\"deviceID\":1,\"action\":\"snap\"}";
        try {
            mqttClient.connect();

            // 구독
            TopicListener topicListener = new TopicListener(subTopic);
            mqttClient.subscribe(topicListener);
//            mqttClient.subscribe(new AWSIotTopic(subTopic));

            // 발행
            mqttClient.publish(pubTopic, payload, 10);
        } catch (AWSIotException e) {
            throw new RuntimeException(e);
        } catch (AWSIotTimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}

