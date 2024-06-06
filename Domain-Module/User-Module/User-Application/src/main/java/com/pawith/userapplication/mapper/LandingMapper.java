package com.pawith.userapplication.mapper;

import com.pawith.userapplication.dto.request.LandingRequest;
import com.pawith.userdomain.entity.Landing;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LandingMapper {

    public static Landing toLandingEntity(LandingRequest landingRequest) {
        return new Landing(landingRequest.getEmail());
    }

}
