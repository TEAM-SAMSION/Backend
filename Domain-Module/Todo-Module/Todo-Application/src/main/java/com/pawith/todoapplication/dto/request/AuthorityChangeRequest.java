package com.pawith.todoapplication.dto.request;

import com.pawith.tododomain.entity.Authority;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityChangeRequest {
        private Authority authority;
}
