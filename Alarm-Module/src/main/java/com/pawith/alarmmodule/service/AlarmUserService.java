package com.pawith.alarmmodule.service;

import com.pawith.alarmmodule.entity.AlarmUser;
import com.pawith.alarmmodule.repository.AlarmUserRepository;
import com.pawith.alarmmodule.service.dto.request.DeviceTokenSaveRequest;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmUserService {
    private final AlarmUserRepository alarmUserRepository;
    private final UserUtils userUtils;

    public void saveDeviceToken(final DeviceTokenSaveRequest request) {
        final User user = userUtils.getAccessUser();
        alarmUserRepository.findByUserId(user.getId())
            .ifPresentOrElse(
                alarmUser ->{
                    alarmUser.updateDeviceToken(request.getDeviceToken());
                    alarmUser.updateDeviceType(request.getDeviceType());
                },
                () -> {
                    final AlarmUser alarmUser = AlarmUser.builder()
                        .deviceToken(request.getDeviceToken())
                        .deviceType(request.getDeviceType())
                        .build();
                    alarmUserRepository.save(alarmUser);
                }
            );
    }
}
