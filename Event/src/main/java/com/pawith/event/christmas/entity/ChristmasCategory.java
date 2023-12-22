package com.pawith.event.christmas.entity;

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
public class ChristmasCategory {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long categoryId;


    public ChristmasCategory(Long categoryId) {
        this.categoryId = categoryId;
    }
}
