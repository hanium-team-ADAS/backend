package com.hanium.adas.domain.doctor.api;
import com.hanium.adas.domain.doctor.application.AuthService;
import com.hanium.adas.domain.doctor.dto.SignInDto;
import com.hanium.adas.domain.doctor.dto.SignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("doctorAuthController")
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/doctor")
public class doctorAuthController {

    private final AuthService authService;

    @Operation(summary = "🟡")
    @PostMapping("/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody SignInDto signInDto, HttpServletResponse response) {

        return ResponseEntity.ok(authService.signIn(signInDto));
    }

    @Operation(summary = "🔺")
    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody SignUpDto signUpDto, HttpServletResponse response) {
        return ResponseEntity.ok(true);
    }
}

