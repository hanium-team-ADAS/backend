package com.hanium.adas.domain.patient.api;

//import com.hanium.adas.domain.patient.application.AuthService;
import com.hanium.adas.domain.patient.dto.PatientSignUpDto;
import com.hanium.adas.domain.patient.dto.PatientSignInDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("patientAuthController")
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientAuthController {

    //private final AuthService authService;

    @Operation(summary = "🔺")
    @PostMapping("/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody PatientSignInDto patientSignInDto, HttpServletResponse response) {
        return ResponseEntity.ok(true);
    }

    @Operation(summary = "🔺")
    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody PatientSignUpDto patientSignUpDto, HttpServletResponse response) {
        return ResponseEntity.ok(true);
    }
}
