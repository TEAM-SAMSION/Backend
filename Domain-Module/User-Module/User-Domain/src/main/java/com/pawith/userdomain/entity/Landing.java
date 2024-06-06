package com.pawith.userdomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Landing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "landing_id")
    private Long id;

    private String email;

    public Landing(String email) {
        this.email = email;
    }

}
