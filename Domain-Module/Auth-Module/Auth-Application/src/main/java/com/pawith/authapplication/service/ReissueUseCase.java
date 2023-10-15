package com.pawith.authapplication.service;

import com.pawith.authapplication.dto.TokenReissueResponse;

public interface ReissueUseCase {
    TokenReissueResponse reissue(String refreshToken);
}
