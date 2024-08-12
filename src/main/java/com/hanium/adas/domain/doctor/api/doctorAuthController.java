package com.hanium.adas.domain.doctor.api;
import com.hanium.adas.domain.doctor.application.DoctorAuthService;
import com.hanium.adas.domain.doctor.dto.DoctorSignInDto;
import com.hanium.adas.domain.doctor.dto.DoctorSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("doctorAuthController")
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class doctorAuthController {

    private final DoctorAuthService authService;

    @Operation(summary = "🟡")
    @PostMapping("/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody DoctorSignInDto signInDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signIn(signInDto));
    }

    @Operation(summary = "🟡")
    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody DoctorSignUpDto doctorSignUpDto, HttpServletResponse response) {
        return ResponseEntity.ok(authService.signUp(doctorSignUpDto));
    }
}

