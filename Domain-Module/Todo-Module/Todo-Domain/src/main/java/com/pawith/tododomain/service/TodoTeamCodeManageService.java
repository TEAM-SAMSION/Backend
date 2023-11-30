package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class TodoTeamCodeManageService {
    private static final Map<String, String> occupiedTodoTeamCodeExpiredMap = ExpiringMap.builder()
        .expiration(1, TimeUnit.HOURS)
        .expirationPolicy(ExpirationPolicy.CREATED)
        .build();

    private final TodoTeamRepository todoTeamRepository;

    public String generateRandomCode() {
        while (true) {
            final String randomUUID = UUID.randomUUID().toString();
            final String[] split = randomUUID.split("-");
            if (!todoTeamRepository.existsByTeamCode(split[0]) && !occupiedTodoTeamCodeExpiredMap.containsKey(split[0])) {
                occupiedTodoTeamCodeExpiredMap.put(split[0], split[0]);
                return split[0];
            }
        }
    }
}
