package com.pawith.todoapplication.dto.response;

import com.pawith.tododomain.entity.Authority;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterSearchInfoResponse {
    private Long registerId;
    private Authority authority;
    private String registerName;
    private String registerEmail;
    private String profileImage;
}
