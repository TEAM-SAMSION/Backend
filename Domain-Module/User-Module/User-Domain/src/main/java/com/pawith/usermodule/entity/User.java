package com.pawith.usermodule.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String imageUrl;
    private String provider;


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
