package com.hanium.adas.domain.appointment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentPatientCancellationDto {
    private Long id;

    // 기본 생성자
    public AppointmentPatientCancellationDto() {}

    // 모든 필드를 포함하는 생성자
    public AppointmentPatientCancellationDto(Long id) {
        this.id = id;
    }
}
