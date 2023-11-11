package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE register SET is_deleted = true WHERE register_id = ?")
@Where(clause = "is_deleted = false")
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

    private Boolean isDeleted=Boolean.FALSE;

    private Boolean isRegistered=Boolean.TRUE;

    @Builder
    public Register(Authority authority, TodoTeam todoTeam, Long userId) {
        this.authority = authority;
        this.todoTeam = todoTeam;
        this.userId = userId;
    }

    public void updateAuthority(Authority authority) {
        if(this.authority.equals(authority))
            return;
        this.authority = Objects.requireNonNull(authority, "authority must be not null");
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

}
