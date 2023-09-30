package com.pawith.authdomain.service;

import com.pawith.authdomain.entity.Token;

public interface TokenDeleteService {

    void deleteRefreshToken(final Token token);
}
