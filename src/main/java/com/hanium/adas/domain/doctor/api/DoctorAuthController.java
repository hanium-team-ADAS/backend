package com.hanium.adas.domain.doctor.api;
import com.hanium.adas.domain.doctor.application.DoctorAuthService;
import com.hanium.adas.domain.doctor.dto.DoctorSignInDto;
import com.hanium.adas.domain.doctor.dto.DoctorSignUpDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorAuthController {

    private final DoctorAuthService doctorauthService;

    @Operation(summary = "🟡")
    @PostMapping("/sign-in")
    public ResponseEntity<Boolean> signIn(@RequestBody DoctorSignInDto doctorsignInDto, HttpServletResponse response) {
        Long id = doctorauthService.signIn(doctorsignInDto);

        Cookie cookieForRole = new Cookie("role", "D"); // 의사는 D, 환자는 P
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
    public ResponseEntity<Boolean> signUp(@RequestBody DoctorSignUpDto doctorSignUpDto, HttpServletResponse response) {
        return ResponseEntity.ok(doctorauthService.signUp(doctorSignUpDto));
    }
}

