package com.hanium.adas.domain.temperature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TemperatureAIModelDto {
    private String[][] camera_temp;
    private String image_path;
    private int age;
    private int gender;
}
