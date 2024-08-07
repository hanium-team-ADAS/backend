package com.hanium.adas.domain.doctor.application;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.doctor.dto.SignInDto;
import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service("doctorAuthService") // 빈 이름을 명시적으로 지정
@RequiredArgsConstructor
public class AuthService {

    private final DoctorRepository doctorRepository;

    public boolean signIn(SignInDto dto) {

        Optional<Doctor> doctor = doctorRepository.findAllByEmail(dto.getEmail());

        if(doctor.isEmpty() || !doctor.get().getPassword().equals(dto.getPassword())) {
            return false;
        } else {
            return true;
        }
    }
}
