package com.ardwiinoo.loansapi.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "authentications")
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}