package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.request.PathHistoryCreateRequest;
import com.pawith.userapplication.mapper.UserMapper;
import com.pawith.userdomain.entity.PathHistory;
import com.pawith.userdomain.service.PathHistorySaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional
public class PathHistoryCreateUseCase {
    private final PathHistorySaveService pathHistroySaveService;

    public void createPathHistory(PathHistoryCreateRequest request){
        PathHistory pathHistory = UserMapper.toPathHistoryEntity(request);
        pathHistroySaveService.savePathHistoryEntity(pathHistory);
    }
}
