package com.pawith.authdomain.entity;

import com.pawith.commonmodule.enums.Provider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    private String sub; // 소셜로그인 유저의 고유 식별자
    private Long userId;

    private OAuth(Provider provider, String sub, Long userId) {
        this.provider = provider;
        this.sub = sub;
        this.userId = userId;
    }

    public static OAuth of(Provider provider, String sub, Long userId){
        return new OAuth(provider,  sub, userId);
    }
}
