package com.hanium.adas.domain.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailDto {
    private Long doctorId;            // 의사의 고유 ID
    private String name;              // 의사 이름
    private String specialization;    // 전문 분야
}