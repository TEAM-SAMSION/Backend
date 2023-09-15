package com.pawith.userpresentation;

import com.pawith.usermodule.dto.response.UserInfoResponse;
import com.pawith.usermodule.service.UserInfoGetUseCase;
import com.pawith.usermodule.service.UserNicknameChangeUseCase;
import com.pawith.usermodule.dto.request.UserNicknameChangeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserNicknameChangeUseCase userNicknameChangeUseCase;
    private final UserInfoGetUseCase userInfoGetUseCase;

    @PutMapping("/name")
    public void putNicknameOnUser(@RequestBody UserNicknameChangeRequest request){
        userNicknameChangeUseCase.changeUserName(request);
    }

    @GetMapping
    public UserInfoResponse getUserInfo(){
        return userInfoGetUseCase.getUserInfo();
    }
}
