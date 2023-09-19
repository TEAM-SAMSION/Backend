package com.pawith.userdomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.enums.Provider;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    public User(String nickname, String email, String imageUrl, Provider provider) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.provider = provider;
    }

    public void updateNickname(final String nickname) {
        if(this.nickname.equals(nickname))
            return;
        this.nickname = Objects.requireNonNull(nickname, "nickname must be not null");
    }

    public void updateProfileImage(final String imageUrl) {
        if(this.imageUrl.equals(imageUrl))
            return;
        this.imageUrl = Objects.requireNonNull(imageUrl, "imageUrl must be not null");
    }
}
