package com.pawith.todoapplication.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityChangeRequest {

        private String authority;

        public AuthorityChangeRequest(String authority) {
            this.authority = authority;
        }
}
