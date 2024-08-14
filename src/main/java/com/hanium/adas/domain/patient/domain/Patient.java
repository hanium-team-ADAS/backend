package com.hanium.adas.domain.patient.domain;

import com.hanium.adas.global.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자는 접근 수준을 protected로
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자
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
