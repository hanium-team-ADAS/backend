package com.hanium.adas.domain.temperature.api;

import com.hanium.adas.domain.temperature.application.MqttPubSubService;
import com.hanium.adas.domain.temperature.dto.TemperatureResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {

    private final MqttPubSubService mqttPubSubService;

    @Operation(summary = "🟡")
    @GetMapping(value = "/snap")
    public ResponseEntity<TemperatureResponseDto> snapPictures() {
        return ResponseEntity.ok(mqttPubSubService.snap());
    }

}
