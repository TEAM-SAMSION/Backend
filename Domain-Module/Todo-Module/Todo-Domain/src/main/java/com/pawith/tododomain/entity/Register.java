package com.pawith.tododomain.entity;

import com.pawith.commonmodule.domain.BaseEntity;
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

    public void updateAuthority(String authority) {
        if(!this.authority.toString().equals(authority) && authority != null && isValidAuthority(authority)) {
            this.authority = Authority.valueOf(authority);
        }
    }

    private boolean isValidAuthority(String authority) {
        return Arrays.stream(Authority.values())
                .anyMatch(enumValue -> enumValue.name().equals(authority));
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
