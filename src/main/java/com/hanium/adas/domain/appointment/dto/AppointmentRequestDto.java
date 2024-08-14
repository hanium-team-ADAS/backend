package com.hanium.adas.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {
    private Long doctorId;
    private String date;
    private String time;
    private String symptoms;
}
