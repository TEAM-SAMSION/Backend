package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Register extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "register_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todoteam_id")
    private TodoTeam todoTeam;

    private Long userId;

    @Builder
    public Register(Authority authority, TodoTeam todoTeam, Long userId) {
        this.authority = authority;
        this.todoTeam = todoTeam;
        this.userId = userId;
    }

    public void updateAuthority(String authority) {
        if(!this.authority.toString().equals(authority) && authority != null && isValidAuthority(authority)) {
            this.authority = Authority.valueOf(authority);
        }
    }

    private boolean isValidAuthority(String authority) {
        return Arrays.stream(Authority.values())
                .anyMatch(enumValue -> enumValue.name().equals(authority));
    }
}
