package com.hanium.adas.domain.doctor.application;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.doctor.dto.DoctorSignInDto;
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

        if(doctor.isEmpty() || !doctor.get().getPassword().equals(dto.getPassword())) {
            throw new CustomException(INVALID_MEMBER);
        } else {
            return doctor.get().getId();
        }
    }
}
