package com.pawith.userapplication.dto.response;

import com.pawith.userdomain.entity.Authority;
import lombok.Getter;

@Getter
public class UserAuthorityInfoResponse {
    private final Authority authority;

    public UserAuthorityInfoResponse(Authority authority) {
        this.authority = authority;
    }
}
