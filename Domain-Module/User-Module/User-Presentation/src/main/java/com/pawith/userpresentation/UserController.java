package com.pawith.userpresentation;

import com.pawith.userapplication.dto.request.PathHistoryCreateRequest;
import com.pawith.userapplication.dto.request.UserNicknameChangeRequest;
import com.pawith.userapplication.dto.response.UserInfoResponse;
import com.pawith.userapplication.service.PathHistoryCreateUseCase;
import com.pawith.userapplication.service.UserInfoGetUseCase;
import com.pawith.userapplication.service.UserNicknameChangeUseCase;
import com.pawith.userapplication.service.UserProfileImageUpdateUseCase;
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
    private final PathHistoryCreateUseCase pathHistoryCreateUseCase;

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

    @PostMapping("/path")
    public void postPathHistory(@RequestBody PathHistoryCreateRequest pathHistoryCreateRequest){
        pathHistoryCreateUseCase.createPathHistory(pathHistoryCreateRequest);
    }
}
