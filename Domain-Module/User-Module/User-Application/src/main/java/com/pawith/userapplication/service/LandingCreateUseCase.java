package com.pawith.userapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.userapplication.dto.request.LandingRequest;
import com.pawith.userapplication.mapper.LandingMapper;
import com.pawith.userdomain.entity.Landing;
import com.pawith.userdomain.service.LandingSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
public class LandingCreateUseCase {
    private final LandingSaveService landingSaveService;

    @Transactional
    public void createLanding(LandingRequest request){
        Landing landing = LandingMapper.toLandingEntity(request);
        landingSaveService.saveUserLanding(landing);
    }

}
