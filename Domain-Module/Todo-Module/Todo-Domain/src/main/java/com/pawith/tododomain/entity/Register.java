package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import com.pawith.commonmodule.util.DomainFieldUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE register SET is_deleted = true WHERE register_id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "register" , indexes = {
        @Index(name = "idx_register_user_id", columnList = "userId"),
})
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

    private LocalDateTime registerAt;

    private Boolean isDeleted=Boolean.FALSE;

    private Boolean isRegistered=Boolean.TRUE;

    @Builder
    public Register(Authority authority, TodoTeam todoTeam, Long userId) {
        this.authority = authority;
        this.todoTeam = todoTeam;
        this.userId = userId;
        this.registerAt = LocalDateTime.now();
    }

    public void updateAuthority(Authority authority) {
        this.authority = DomainFieldUtils.DomainValidateBuilder.builder(Authority.class)
                .newValue(authority)
                .currentValue(this.authority)
                .validate();
    }

    public void unregister(){
        this.isRegistered = Boolean.FALSE;
    }

    public Boolean isRegistered(){
        return this.isRegistered;
    }

    public Boolean isPresident(){
        return this.authority.equals(Authority.PRESIDENT);
    }

    public Boolean isMember() {
        return this.authority.equals(Authority.MEMBER);
    }

    public void reRegister(){
        this.isRegistered = Boolean.TRUE;
        this.registerAt = LocalDateTime.now();
    }

    public Boolean matchUserId(Long userId) {
        return this.userId.equals(userId);
    }

}
