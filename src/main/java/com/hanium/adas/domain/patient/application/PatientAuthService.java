package com.hanium.adas.domain.patient.application;

import com.hanium.adas.domain.patient.domain.Patient;
import com.hanium.adas.domain.patient.dto.PatientSignInDto;
import com.hanium.adas.domain.patient.dto.PatientSignUpDto;
import com.hanium.adas.domain.patient.repository.PatientRepository;
import com.hanium.adas.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hanium.adas.global.exception.ErrorCode.INVALID_MEMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientAuthService {

    private final PatientRepository patientRepository;

    public Long signIn(PatientSignInDto dto) {
        Optional<Patient> patient = patientRepository.findAllByEmail(dto.getEmail());

        if (patient.isEmpty() || !patient.get().getPassword().equals(dto.getPassword())) {
            throw new CustomException(INVALID_MEMBER);
        } else {
            return patient.get().getId();
        }
    }


    public boolean signUp(PatientSignUpDto dto) {
        // 이메일 중복 확인
        if (patientRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // Patient 엔티티 생성
        Patient patient = Patient.builder()
                .name(dto.getName())
                .dateOfBirth(dto.getDateOfBirth().toString())
                .password(dto.getPassword())  // 비밀번호를 그대로 저장
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender() == 1 ? "Male" : "Female")
                .build();

        // Patient 저장
        patientRepository.save(patient);
        return true;
    }
}
