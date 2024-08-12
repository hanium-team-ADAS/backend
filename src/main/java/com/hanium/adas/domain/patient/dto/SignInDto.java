package com.hanium.adas.domain.patient.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignInDto {
    @NotNull
    private String email;

    @NotNull
    private String password;
}
