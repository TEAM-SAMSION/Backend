package com.petmory.authmodule.application.port.out.observer.subject;

import com.petmory.authmodule.application.dto.Provider;
import com.petmory.commonmodule.subject.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth implements Status {
    private Provider provider;
    private String accessToken;
}
