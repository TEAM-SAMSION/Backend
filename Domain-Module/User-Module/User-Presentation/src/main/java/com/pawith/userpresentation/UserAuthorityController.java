package com.pawith.userpresentation;

import com.pawith.userapplication.dto.response.UserAuthorityInfoResponse;
import com.pawith.userapplication.service.UserAuthorityGetUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserAuthorityController {
    private final UserAuthorityGetUseCase userAuthorityGetUseCase;


    @GetMapping("/authority")
    public UserAuthorityInfoResponse getUserAuthority() {
        return userAuthorityGetUseCase.getUserAuthority();
    }
}
