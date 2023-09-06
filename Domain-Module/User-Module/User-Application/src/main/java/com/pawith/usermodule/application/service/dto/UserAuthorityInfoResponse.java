package com.pawith.usermodule.application.service.dto;

import com.pawith.usermodule.domain.entity.Authority;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserAuthorityInfoResponse {
    private final Authority authority;

    public UserAuthorityInfoResponse(Authority authority) {
        this.authority = authority;
    }
}
