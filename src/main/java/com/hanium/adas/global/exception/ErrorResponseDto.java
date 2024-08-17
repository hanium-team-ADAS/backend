package com.hanium.adas.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseDto {

    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDto> toResponseEntity(ErrorCode e, String message) {
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseDto.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(message)
                        .build()
                );
    }
}
