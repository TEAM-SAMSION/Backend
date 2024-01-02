package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.commonmodule.cache.operators.SetOperator;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class TodoTeamCodeManageService {
    private final TodoTeamRepository todoTeamRepository;
    private final SetOperator<String> occupiedTodoTeamCodeSet;


    public String generateRandomCode() {
        while (true) {
            final String randomUUID = UUID.randomUUID().toString();
            final String[] split = randomUUID.split("-");
            final String randomCode = split[0];
            if (!todoTeamRepository.existsByTeamCode(randomCode) && !occupiedTodoTeamCodeSet.contains(randomCode)) {
                occupiedTodoTeamCodeSet.addWithExpire(randomCode, 1, TimeUnit.HOURS);
                return split[0];
            }
        }
    }
}
