package com.hanium.adas.domain.doctor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DoctorSignUpDto {
    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String specialization;

    @NotNull
    private String phone;

    @NotNull
    private String licenseNumber;
}
