package com.hanium.adas.domain.temperature.application;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.adas.domain.temperature.dto.SensorDto;
import org.springframework.web.client.RestTemplate;

public class TopicListener extends AWSIotTopic {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TopicListener(String topic) {
        super(topic);
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        try {
            String messagePayload = message.getStringPayload();
            SensorDto sensorDto = objectMapper.readValue(messagePayload, SensorDto.class);
            System.out.println(sensorDto);

            TemperatureAIModel temperatureAIModel = new TemperatureAIModel(new RestTemplate());
            System.out.println(temperatureAIModel.getTemperature(sensorDto));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
