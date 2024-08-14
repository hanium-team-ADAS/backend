package com.hanium.adas.domain.doctor.application;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.doctor.dto.DoctorSignInDto;
import com.hanium.adas.domain.doctor.dto.DoctorSignUpDto;
import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import com.hanium.adas.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hanium.adas.global.exception.ErrorCode.INVALID_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorAuthService {

    private final DoctorRepository doctorRepository;

    public Long signIn(DoctorSignInDto dto) {
        Optional<Doctor> doctor = doctorRepository.findAllByEmail(dto.getEmail());

        if (doctor.isEmpty() || !doctor.get().getPassword().equals(dto.getPassword())) {
            throw new CustomException(INVALID_MEMBER);
        } else {
            return doctor.get().getId();
        }

    }

    public boolean signUp(DoctorSignUpDto dto) {
        // 이메일 중복 확인
        if (doctorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // Doctor 엔티티 생성
        Doctor doctor = Doctor.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .specialization(dto.getSpecialization())
                .phone(dto.getPhone())
                .licenseNumber(dto.getLicenseNumber())
                .build();

        // Doctor 저장
        doctorRepository.save(doctor);
        return true;
    }
}
