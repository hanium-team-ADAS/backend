package com.hanium.adas.domain.temperature.application;

import com.hanium.adas.domain.temperature.dto.SensorDto;
import com.hanium.adas.domain.temperature.dto.TemperatureAIModelDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TemperatureAIModel {
    private final RestTemplate restTemplate;
    String url = "http://43.203.121.91:8000/predict/";

    public TemperatureAIModel(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public int getTemperature (SensorDto sensorDto) {
        
        // 나이값 등 추후 수정 필요
        TemperatureAIModelDto dto = new TemperatureAIModelDto(sensorDto.getSensor_data(), sensorDto.getFile_url(), 25, 0);


        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(dto, header);
        final ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Integer.class);
        return response.getBody();
    }
}
