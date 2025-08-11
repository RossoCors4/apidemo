package com.tintin.demo.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class Employee {
    @Column(unique = true)
    private @jakarta.persistence.Id String emailAdress;
    private String lastName;
    private String firstName;
}
