package com.pawith.todoapplication.mapper;

import com.pawith.todoapplication.dto.request.PetRegisterRequest;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.entity.vo.PetSpecies;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PetMapper {

    public static Pet mapToPet(PetRegisterRequest petRegister, TodoTeam todoTeam, String imageUrl){
        return Pet.builder()
            .todoTeam(todoTeam)
            .name(petRegister.getName())
            .description(petRegister.getDescription())
            .age(petRegister.getAge())
            .imageUrl(imageUrl)
            .petSpecies(PetSpecies.builder()
                .genus(petRegister.getGenus())
                .species(petRegister.getSpecies())
                .build())
            .build();
    }
}
