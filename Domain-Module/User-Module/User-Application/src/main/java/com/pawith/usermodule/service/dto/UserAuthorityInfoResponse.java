package com.pawith.usermodule.service.dto;

import com.pawith.usermodule.entity.Authority;
import lombok.Getter;

@Getter
public class UserAuthorityInfoResponse {
    private final Authority authority;

    public UserAuthorityInfoResponse(Authority authority) {
        this.authority = authority;
    }
}
