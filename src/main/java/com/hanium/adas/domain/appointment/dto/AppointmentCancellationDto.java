package com.hanium.adas.domain.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentCancellationDto {
    private Long id;

    // 기본 생성자
    public AppointmentCancellationDto() {}

    // 모든 필드를 포함하는 생성자
    public AppointmentCancellationDto(Long id) {
        this.id = id;
    }
}
