package com.hanium.adas.domain.appointment.api;

import com.hanium.adas.domain.appointment.application.AppointmentService;
import com.hanium.adas.domain.appointment.dto.AppointmentCancellationDto;
import com.hanium.adas.domain.appointment.dto.AppointmentRequestDto;
import com.hanium.adas.domain.appointment.dto.AppointmentsDto;
import com.hanium.adas.domain.appointment.dto.DoctorDetailDto;
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
    public ResponseEntity<Boolean> createAppointment(@RequestBody AppointmentRequestDto appointmentRequestDto, @RequestParam Long patientId) {
        boolean isCreated = appointmentService.createAppointment(appointmentRequestDto, patientId);
        return ResponseEntity.ok(isCreated);
    }

    @Operation(summary = "🟡")
    @GetMapping("/patient/{patientId}")
    public List<AppointmentsDto> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @Operation(summary = "🟡")
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelAppointment(@RequestBody AppointmentCancellationDto appointmentCancellationDto) {
        boolean isCancelled = appointmentService.cancelAppointment(appointmentCancellationDto);
        return ResponseEntity.ok(isCancelled);
    }
}
