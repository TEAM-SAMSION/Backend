package com.pawith.todoapplication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterTermResponse {
    private RegisterTerm registerTerm;

    public enum RegisterTerm {
        FIRST_WEEK,
        SECOND_WEEK,
        AFTER_SECOND_WEEK
    }

    public RegisterTermResponse(Integer registerTerm) {
        if (registerTerm >= 0 && registerTerm <= 6) {
            this.registerTerm = RegisterTerm.FIRST_WEEK;
        } else if (registerTerm > 6 && registerTerm <= 13) {
            this.registerTerm = RegisterTerm.SECOND_WEEK;
        } else {
            this.registerTerm = RegisterTerm.AFTER_SECOND_WEEK;
        }
    }
}
