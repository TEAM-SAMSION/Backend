package com.pawith.authdomain.entity;

import com.pawith.authdomain.jwt.TokenType;
import com.pawith.commonmodule.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE token SET is_deleted = true WHERE token_id = ?")
@Where(clause = "is_deleted = false")
@Getter
public class Token extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Long userId;
    private String value;
    private Boolean isDeleted = Boolean.FALSE;

    @Builder
    public Token(TokenType tokenType, Long userId, String value) {
        this.tokenType = tokenType;
        this.userId = userId;
        this.value = value;
    }
}
