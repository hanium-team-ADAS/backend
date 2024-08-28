package com.hanium.adas.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAppointmentsDto {
    private Long appointmentId;
    private Long patientId;
    private String patientName;
    private String date; // 진료일자
    private String time; // 진료시간
    private String symptoms; // 증상
    private int status; // 예약 상태
    private Boolean isDeleted; //환자의 진료취소 여부
}
