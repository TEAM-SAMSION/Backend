package com.pawith.userdomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.enums.Provider;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "user", indexes = {
    @Index(name = "idx_user_email", columnList = "email")
})
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
        this.nickname = DomainFieldUtils.updateIfDifferent(this.nickname, nickname);
    }

    public void updateProfileImage(final String imageUrl) {
        this.imageUrl = DomainFieldUtils.updateIfDifferent(this.imageUrl, imageUrl);
    }

    public Boolean isNotMatchingProvider(Provider provider) {
        return !this.provider.equals(provider);
    }

    public Long getJoinTerm() {
        LocalDate nowDate = LocalDate.now();
        LocalDate joinDate = this.createdAt.toLocalDate();
        return ChronoUnit.DAYS.between(joinDate, nowDate) + 1;
    }
}
