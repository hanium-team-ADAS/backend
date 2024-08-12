package com.hanium.adas.domain.appointment.application;

import com.hanium.adas.domain.appointment.dto.DoctorDetailDto;
import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    @Autowired
    private final DoctorRepository doctorRepository;

    public List<DoctorDetailDto> getDoctorDetails() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctor -> DoctorDetailDto.builder()
                        .doctorId(doctor.getId())
                        .name(doctor.getName())
                        .specialization(doctor.getSpecialization())
                        .build())
                .collect(Collectors.toList());
    }

}
