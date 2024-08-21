package com.hanium.adas.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentsDto {
    private Long id;
    private Long patientId;
    private String date;
    private String time;
    private String symptoms;
    private DoctorDetailDto doctor;
    private Boolean deleted;
    private int status;
}