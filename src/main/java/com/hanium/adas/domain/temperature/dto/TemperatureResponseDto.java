package com.hanium.adas.domain.temperature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class TemperatureResponseDto {
    public TemperatureResponseDto() {
        this.isSuccess = false;
    }

    private boolean isSuccess;
    private int temperature;
}
