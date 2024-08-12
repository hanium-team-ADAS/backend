package com.hanium.adas.domain.appointment.api;

import com.hanium.adas.domain.appointment.application.AppointmentService;
import com.hanium.adas.domain.appointment.dto.DoctorDetailDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
