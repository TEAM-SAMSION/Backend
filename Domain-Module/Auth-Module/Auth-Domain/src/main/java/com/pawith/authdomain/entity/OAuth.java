package com.pawith.authdomain.entity;

import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.util.DomainFieldUtils;
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
    private String refreshToken;
    private String email;
    private String sub;
    private Long userId;

    private OAuth(Provider provider, String refreshToken, String email, String sub, Long userId) {
        this.provider = provider;
        this.refreshToken = refreshToken;
        this.email = email;
        this.sub = sub;
        this.userId = userId;
    }

    public static OAuth of(Provider provider, String refreshToken, String email, String sub, Long userId){
        return new OAuth(provider, refreshToken, email, sub, userId);
    }

    public void updateEmail(String email){
        this.email = DomainFieldUtils.DomainValidateBuilder.builder(String.class)
            .newValue(email)
            .currentValue(this.email)
            .validate();
    }

    public boolean isEqualEmail(String email){
        return this.email.equals(email);
    }
}
