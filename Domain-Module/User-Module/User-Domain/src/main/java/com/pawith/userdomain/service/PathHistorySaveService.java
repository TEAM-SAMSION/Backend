package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.PathHistory;
import com.pawith.userdomain.repository.PathHistoryRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class PathHistorySaveService {
    private final PathHistoryRepository pathHistoryRepository;

    public void savePathHistoryEntity(PathHistory pathHistory) {
        pathHistoryRepository.save(pathHistory);
    }
}
