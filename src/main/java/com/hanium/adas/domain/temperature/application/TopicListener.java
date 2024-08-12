package com.hanium.adas.domain.temperature.application;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanium.adas.domain.temperature.dto.SensorDto;
import com.hanium.adas.domain.temperature.dto.TemperatureAIModelDto;
import com.hanium.adas.domain.temperature.dto.TemperatureResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class TopicListener extends AWSIotTopic {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TemperatureResponseDto temperatureResponseDto;

    public TopicListener(String topic, TemperatureResponseDto temperatureResponseDto) {
        super(topic);
        this.temperatureResponseDto = temperatureResponseDto;
     
    }

    @Override
    public void onMessage(AWSIotMessage message) {

        log.info("onMessage 실행됨!!!!");

        try {
            String messagePayload = message.getStringPayload();
            SensorDto sensorDto = objectMapper.readValue(messagePayload, SensorDto.class);
            log.info(sensorDto.toString());

            TemperatureAIModel temperatureAIModel = new TemperatureAIModel(new RestTemplate());
            int temp = temperatureAIModel.getTemperature(sensorDto);
            log.info(Integer.toString(temp));

            temperatureResponseDto.setTemperature(temp);
            temperatureResponseDto.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
