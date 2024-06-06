package com.pawith.userdomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.userdomain.entity.Landing;
import com.pawith.userdomain.repository.LandingRepository;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class LandingSaveService {
        private final LandingRepository landingRepository;

        public void saveUserLanding(Landing landing) {
            landingRepository.save(landing);
        }

}
