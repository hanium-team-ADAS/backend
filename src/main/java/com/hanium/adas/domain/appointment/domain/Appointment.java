package com.hanium.adas.domain.appointment.domain;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.patient.domain.Patient;
import com.hanium.adas.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;



@Builder
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 접근 수준을 protected로
@AllArgsConstructor
public class Appointment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    private String date; //진료일자
    private String time; //진료시간
    private String symptoms; //증상
    private Boolean isDeleted = false;  // 환자기준 삭제 여부

    private String symptom;

    @Setter
    private int status; //의사기준 0 예약요청  1 진료승인  2 진료거절

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
