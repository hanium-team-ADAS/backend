package com.hanium.adas.domain.appointment.application;

import com.hanium.adas.domain.appointment.domain.Appointment;
import com.hanium.adas.domain.appointment.dto.DoctorDetailDto;
import com.hanium.adas.domain.appointment.dto.AppointmentRequestDto;

import com.hanium.adas.domain.doctor.domain.Doctor;
import com.hanium.adas.domain.patient.domain.Patient;

import com.hanium.adas.domain.doctor.repository.DoctorRepository;
import com.hanium.adas.domain.patient.repository.PatientRepository;
import com.hanium.adas.domain.appointment.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping
@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

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


    public boolean createAppointment(AppointmentRequestDto appointmentRequestDto, Long patientId) {
        Optional<Doctor> doctor = doctorRepository.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = patientRepository.findById(patientId);

        if (doctor.isPresent() && patient.isPresent()) {
            Appointment appointment = Appointment.builder()
                    .doctor(doctor.get())
                    .patient(patient.get())
                    .date(appointmentRequestDto.getDate())
                    .time(appointmentRequestDto.getTime())
                    .symptoms(appointmentRequestDto.getSymptoms())
                    .deleted(false)
                    .build();

            appointmentRepository.save(appointment);

            return true;
        } else {
            throw new IllegalArgumentException("Doctor or Patient not found");
        }
    }


}
