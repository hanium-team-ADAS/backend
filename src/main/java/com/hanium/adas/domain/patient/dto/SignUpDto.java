package com.hanium.adas.domain.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpDto {
    @NotNull
    private String name;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private int gender;
}

