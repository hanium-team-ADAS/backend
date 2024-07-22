package com.hanium.adas.domain.temperature;

import com.hanium.adas.domain.appointment.domain.Appointment;
import com.hanium.adas.global.common.BaseTimeEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Temperature extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Appointment appointment;

    private String bodyTemperature;
}
