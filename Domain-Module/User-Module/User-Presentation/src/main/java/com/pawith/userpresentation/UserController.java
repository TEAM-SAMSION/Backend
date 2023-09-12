package com.pawith.userpresentation;

import com.pawith.usermodule.application.service.UserNicknameChangeUseCase;
import com.pawith.usermodule.application.service.dto.UserNicknameChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserNicknameChangeUseCase userNicknameChangeUseCase;

    @PutMapping("/name")
    public void putNicknameOnUser(@RequestBody UserNicknameChangeRequest request){
        userNicknameChangeUseCase.changeUserName(request);
    }
}
