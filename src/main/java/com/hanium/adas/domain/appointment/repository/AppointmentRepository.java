package com.hanium.adas.domain.appointment.repository;


import com.hanium.adas.domain.appointment.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // 필요한 추가적인 쿼리 메서드를 정의할 수 있습니다.
}
