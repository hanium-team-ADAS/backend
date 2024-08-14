package com.hanium.adas.domain.appointment.dto;

import lombok.Getter;

@Getter
public class AppointmentRequestDto {
    private Long doctorId;
    private String date;
    private String time;
    private String symptoms;
}
