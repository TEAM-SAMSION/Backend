package com.pawith.authmodule.application.port.out.observer.subject;

import com.pawith.authmodule.application.dto.Provider;
import com.pawith.commonmodule.observer.subject.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth implements Status {
    private Provider provider;
    private String accessToken;
}
