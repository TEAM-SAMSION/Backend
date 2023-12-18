package com.pawith.alarmmodule.service;

import com.pawith.alarmmodule.entity.AlarmUser;
import com.pawith.alarmmodule.exception.AlarmError;
import com.pawith.alarmmodule.exception.AlarmException;
import com.pawith.alarmmodule.repository.AlarmUserRepository;
import com.pawith.alarmmodule.service.dto.request.DeviceTokenSaveRequest;
import com.pawith.userdomain.entity.User;
import com.pawith.userdomain.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                alarmUser -> alarmUser.updateDeviceToken(request.getDeviceToken()),
                () -> saveNewDeviceToken(request, user)
            );
    }

    private void saveNewDeviceToken(DeviceTokenSaveRequest request, User user) {
        final AlarmUser alarmUser = AlarmUser.builder()
            .deviceToken(request.getDeviceToken())
            .userId(user.getId())
            .build();
        alarmUserRepository.save(alarmUser);
    }

    public AlarmUser findDeviceTokenByUserId(Long userId) {
        return alarmUserRepository.findByUserId(userId)
            .orElseThrow(() -> new AlarmException(AlarmError.DEVICE_TOKEN_NOT_FOUND));
    }

    public Map<Long, AlarmUser> findAlarmUsersByUserIds(List<Long> userIds) {
        return alarmUserRepository.findByUserIdIn(userIds).stream()
            .collect(Collectors.toMap(AlarmUser::getUserId, Function.identity()));
    }
}
