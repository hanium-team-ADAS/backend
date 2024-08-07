package com.hanium.adas.domain.patient.application;

import com.hanium.adas.domain.patient.domain.Patient;
import com.hanium.adas.domain.patient.dto.SignInDto;
import com.hanium.adas.domain.patient.dto.SignUpDto;
import com.hanium.adas.domain.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("patientAuthService") // 빈 이름을 명시적으로 지정
@RequiredArgsConstructor
public class AuthService {

    private final PatientRepository patientRepository;

    public boolean signIn(SignInDto dto) {

        Optional<Patient> patient = patientRepository.findAllByEmail(dto.getEmail());

        return patient.isPresent() && patient.get().getPassword().equals(dto.getPassword());
    }

    public boolean signUp(SignUpDto dto) {
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
