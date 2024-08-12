package com.hanium.adas.domain.temperature.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Data
@Getter
@RequiredArgsConstructor
public class SensorDto {
    private String[][] sensor_data;
    private String file_url;
}
