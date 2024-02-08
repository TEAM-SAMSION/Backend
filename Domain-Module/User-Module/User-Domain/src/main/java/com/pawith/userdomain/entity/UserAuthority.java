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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private UserAuthority(User user, String email) {
        this.authority = Authority.GUEST;
        this.user = user;
        this.email = email;
    }

    public static UserAuthority of(User user, String email) {
        return new UserAuthority(user,email);
    }

    public void changeUserAuthority(){
        if(this.authority.equals(Authority.GUEST)){
            this.authority = Authority.USER;
        }
    }

    public void initialUserAuthority(){
        this.authority = Authority.GUEST;
    }
}
