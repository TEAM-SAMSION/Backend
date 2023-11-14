package com.pawith.userdomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.enums.Provider;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id = ?")
@Where(clause = "is_deleted = false")
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

    private Boolean isDeleted = Boolean.FALSE;

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

    public Boolean isNotMatchingProvider(Provider provider) {
        return !this.provider.equals(provider);
    }
}
