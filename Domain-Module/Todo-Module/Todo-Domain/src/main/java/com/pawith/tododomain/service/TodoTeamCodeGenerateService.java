package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.TodoTeamRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class TodoTeamCodeGenerateService {
    private final TodoTeamRepository todoTeamRepository;

    // TODO: 2023/09/20 랜덤 코드 생성이 너무 오래걸리면 어떻게할지 고민해보기
    public String generateRandomCode(){
        while(true){
            final String randomUUID = UUID.randomUUID().toString();
            final String[] split = randomUUID.split("-");
            if(!todoTeamRepository.existsByTeamCode(split[0])){
                return split[0];
            }
        }
    }
}
