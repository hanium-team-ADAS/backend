package com.hanium.adas.domain.appointment.application;

import com.hanium.adas.domain.appointment.domain.Appointment;
import com.hanium.adas.domain.appointment.dto.AppointmentPatientCancellationDto;
import com.hanium.adas.domain.appointment.dto.AppointmentsDto;
import com.hanium.adas.domain.appointment.dto.DoctorDetailDto;
import com.hanium.adas.domain.appointment.dto.AppointmentPatientRequestDto;

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
                            .id(doctor.getId())
                            .name(doctor.getName())
                            .specialization(doctor.getSpecialization())
                            .build())
                    .collect(Collectors.toList());
    }


    public boolean createAppointment(AppointmentPatientRequestDto appointmentRequestDto, Long patientId) {
        Optional<Doctor> doctor = doctorRepository.findById(appointmentRequestDto.getDoctorId());
        Optional<Patient> patient = patientRepository.findById(patientId);

        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return false;
        }

        Optional<Doctor> doctorOpt = doctorRepository.findById(appointmentRequestDto.getDoctorId());
        if (doctorOpt.isEmpty()) {
            return false;
        }

        Appointment appointment = Appointment.builder()
                .doctor(doctorOpt.get())
                .patient(patientOpt.get())
                .date(appointmentRequestDto.getDate())
                .time(appointmentRequestDto.getTime())
                .symptoms(appointmentRequestDto.getSymptoms())
                .deleted(false)
                .status(0)
                .build();

        appointmentRepository.save(appointment);
        return true;
    }

    public List<AppointmentsDto> getAppointmentsByPatientId(Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        return appointments.stream()
                .map(appointment -> AppointmentsDto.builder()
                        .id(appointment.getId())
                        .patientId(appointment.getPatient().getId())
                        .date(appointment.getDate())
                        .time(appointment.getTime())
                        .symptoms(appointment.getSymptoms())
                        .deleted(appointment.getDeleted())
                        .status(appointment.getStatus())
                        .doctor(DoctorDetailDto.builder()
                                .id(appointment.getDoctor().getId())
                                .name(appointment.getDoctor().getName())
                                .specialization(appointment.getDoctor().getSpecialization())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public boolean cancelAppointment(AppointmentPatientCancellationDto appointmentCancellationDto) {
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentCancellationDto.getId());
        if (appointmentOpt.isEmpty()) {
            return false;
        }

        Appointment appointment = appointmentOpt.get();
        appointment.setDeleted(true); // 예약을 취소 상태로 변경
        appointmentRepository.save(appointment);
        return true;
    }
}
