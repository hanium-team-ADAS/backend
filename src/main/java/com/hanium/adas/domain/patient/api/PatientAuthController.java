package com.hanium.adas.domain.patient.api;

import com.hanium.adas.domain.patient.application.PatientAuthService;
import com.hanium.adas.domain.patient.dto.PatientSignInDto;
import com.hanium.adas.domain.patient.dto.PatientSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
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

    private final PatientAuthService patientAuthService;

    @Operation(summary = "🟡")
    @PostMapping("/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody PatientSignInDto patientsignInDto, HttpServletResponse response) {
        Long id = patientAuthService.signIn(patientsignInDto);

        Cookie cookieForRole = new Cookie("role", "P"); // 의사는 D, 환자는 P
        Cookie cookieForId = new Cookie("id", id.toString());

        cookieForRole.setPath("/");
        cookieForId.setPath("/");

        cookieForRole.setMaxAge(3 * 60 * 60);
        cookieForId.setMaxAge(3 * 60 * 60);

        response.addCookie(cookieForRole);
        response.addCookie(cookieForId);


        return ResponseEntity.ok(true);
    }

    @Operation(summary = "🟡")
    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody PatientSignUpDto patientSignUpDto, HttpServletResponse response) {
        return ResponseEntity.ok(patientAuthService.signUp(patientSignUpDto));
    }
}
