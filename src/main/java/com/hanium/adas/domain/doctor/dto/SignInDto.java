package com.hanium.adas.domain.doctor.dto;

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
