package com.hanium.adas.domain.appointment.repository;


import com.hanium.adas.domain.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // 환자의 ID를 기준으로 예약 내역 조회
    List<Appointment> findByPatientId(Long patientId);
}
