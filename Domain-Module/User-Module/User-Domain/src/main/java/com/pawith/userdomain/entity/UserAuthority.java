package com.pawith.userdomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
        if(this.authority.equals(Authority.GUEST)){
            this.authority = Authority.USER;
        }
    }
}
