package com.hanium.adas.domain.patient.domain;

import com.hanium.adas.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Patient extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String dateOfBirth;
    private String password;
    private String email;
    private String phone;
    private String gender;
}
