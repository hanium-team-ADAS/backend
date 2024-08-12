package com.hanium.adas.domain.doctor.application;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.doctor.dto.DoctorSignInDto;
import com.hanium.adas.domain.doctor.dto.DoctorSignUpDto;
import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("doctorAuthService") // 빈 이름을 명시적으로 지정
@RequiredArgsConstructor
public class DoctorAuthService {

    private final DoctorRepository doctorRepository;

    public boolean signIn(DoctorSignInDto dto) {

        Optional<Doctor> doctor = doctorRepository.findAllByEmail(dto.getEmail());

        return doctor.isPresent() && doctor.get().getPassword().equals(dto.getPassword());
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
