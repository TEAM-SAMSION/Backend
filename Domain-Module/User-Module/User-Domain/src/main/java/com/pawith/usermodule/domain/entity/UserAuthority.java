package com.pawith.usermodule.domain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthority extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_authority_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Authority authority;
    private String email;

    public UserAuthority(String email) {
        this.authority = Authority.GUEST;
        this.email = email;
    }

    public void changeUserAuthority(){
        this.authority = Authority.USER;
    }
}
