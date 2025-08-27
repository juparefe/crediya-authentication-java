package co.com.pragma.r2dbc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigInteger;
import java.time.LocalDate;

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    private Double salary;
}
