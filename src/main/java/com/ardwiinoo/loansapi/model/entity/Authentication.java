package com.ardwiinoo.loansapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authentications")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}