package com.hanium.adas.domain.doctor.repository;

import com.hanium.adas.domain.doctor.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findAllByEmail(String email);

}
