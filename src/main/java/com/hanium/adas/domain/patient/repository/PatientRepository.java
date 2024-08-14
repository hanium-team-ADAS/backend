package com.hanium.adas.domain.patient.repository;

import com.hanium.adas.domain.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findAllByEmail(String email);
    boolean existsByEmail(String email);
}
