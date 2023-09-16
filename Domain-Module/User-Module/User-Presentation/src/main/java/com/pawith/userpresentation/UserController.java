package com.pawith.userpresentation;

import com.pawith.usermodule.dto.request.UserNicknameChangeRequest;
import com.pawith.usermodule.dto.response.UserInfoResponse;
import com.pawith.usermodule.service.UserInfoGetUseCase;
import com.pawith.usermodule.service.UserNicknameChangeUseCase;
import com.pawith.usermodule.service.UserProfileImageUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserNicknameChangeUseCase userNicknameChangeUseCase;
    private final UserInfoGetUseCase userInfoGetUseCase;
    private final UserProfileImageUpdateUseCase userProfileImageUpdateUseCase;

    @PutMapping("/name")
    public void putNicknameOnUser(@RequestBody UserNicknameChangeRequest request){
        userNicknameChangeUseCase.changeUserName(request);
    }

    @GetMapping
    public UserInfoResponse getUserInfo(){
        return userInfoGetUseCase.getUserInfo();
    }

    @PostMapping(consumes = "multipart/form-data")
    public void postUserProfileImage(@RequestPart(name = "profileImage") MultipartFile request){
        userProfileImageUpdateUseCase.updateUserProfileImage(request);
    }
}
