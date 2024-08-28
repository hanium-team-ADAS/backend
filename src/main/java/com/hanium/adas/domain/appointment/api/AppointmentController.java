package com.hanium.adas.domain.appointment.api;

import com.hanium.adas.domain.appointment.application.AppointmentService;
import com.hanium.adas.domain.appointment.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "🟡")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDetailDto>> getDoctorDetails() {
        List<DoctorDetailDto> doctorDetails = appointmentService.getDoctorDetails();
        return ResponseEntity.ok(doctorDetails);
    }

    @Operation(summary = "🟡")
    @PostMapping("/create")
    public ResponseEntity<Boolean> createAppointment(@RequestBody AppointmentPatientRequestDto appointmentRequestDto, @RequestParam Long patientId) {
        boolean isCreated = appointmentService.createAppointment(appointmentRequestDto, patientId);
        return ResponseEntity.ok(isCreated);
    }

    @Operation(summary = "🟡")
    @GetMapping("/patient/{patientId}")
    public List<PatientAppointmentsDto> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @Operation(summary = "🟡")
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelAppointment(@RequestBody AppointmentPatientCancellationDto appointmentCancellationDto) {
        boolean isCancelled = appointmentService.cancelAppointment(appointmentCancellationDto);
        return ResponseEntity.ok(isCancelled);
    }

    @Operation(summary = "🟡")
    @PutMapping("/update-status")
    public ResponseEntity<Boolean> doctorUpdateAppointmentStatus(
            @RequestParam Long appointmentId,
            @RequestParam int status) {

        boolean isUpdated = appointmentService.doctorUpdateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok(isUpdated);
    }

    @Operation(summary = "🟡")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAppointmentsDto>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        List<DoctorAppointmentsDto> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
        return ResponseEntity.ok(appointments);
    }

}
