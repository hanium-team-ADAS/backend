package com.hanium.adas.domain.temperature.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/picture")
public class PictureController {
    @GetMapping(value = "/snap")
    public ResponseEntity<Boolean> snapPictures() {
        return ResponseEntity.ok(true);
    }

}
