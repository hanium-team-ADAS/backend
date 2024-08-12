package com.hanium.adas.domain.temperature.application;

import com.amazonaws.services.iot.client.*;
import com.hanium.adas.domain.temperature.config.MqttConfig;
import com.hanium.adas.domain.temperature.dto.TemperatureResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MqttPubSubService {

    @Autowired
    private AWSIotMqttClient mqttClient;

    // 발행, 구독을 위한 값
    static final private String subTopic = "pic";
    static final private String pubTopic = "snap";


    public TemperatureResponseDto snap() {
        TemperatureResponseDto temperatureResponseDto = new TemperatureResponseDto();

        // mqttClient 설정
//        mqttClient = new AWSIotMqttClient(
//                mqttConfig.getClientEndpoint(),
//                mqttConfig.getClientId(),
//                mqttConfig.getAccessKeyId(),
//                mqttConfig.getSecretAccessKey()
//        );
//        mqttClient.setConnectionTimeout(30);
//        mqttClient.setServerAckTimeout(10);

        String payload = "{\"deviceID\":1,\"action\":\"snap\"}";
        try {

            if (!mqttClient.getConnectionStatus().equals(AWSIotConnectionStatus.CONNECTED)) {
                mqttClient.connect();
            }

            // 구독
            TopicListener topicListener = new TopicListener(subTopic, temperatureResponseDto);
            mqttClient.subscribe(topicListener);

            // 발행
            mqttClient.publish(pubTopic, payload, 10);

            // 체온 측정 완료 대기 (20초간)
            for (int i = 0; i < 20; i++) {

                Thread.sleep(1000);

                // 성공하면 탈출
                if(temperatureResponseDto.isSuccess()) {
                    break;
                }
            }

        } catch (AWSIotException e) {
            throw new RuntimeException(e);
        } catch (AWSIotTimeoutException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 구독 해제 및 연결해제
//        try {
//            mqttClient.unsubscribe(subTopic);
//            mqttClient.disconnect();
//        } catch (AWSIotException e) {
//            throw new RuntimeException(e);
//        }

        return temperatureResponseDto;
    }

}

