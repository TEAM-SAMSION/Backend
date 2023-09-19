package com.pawith.tododomain.service;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.repository.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.then;

@Slf4j
@UnitTestConfig
@DisplayName("PetSaveService 테스트")
class PetSaveServiceTest {

    @Mock
    private PetRepository petRepository;

    private PetSaveService petSaveService;

    @BeforeEach
    void init() {
        petSaveService = new PetSaveService(petRepository);
    }

    @Test
    @DisplayName("Pet 엔티티를 저장한다.")
    void savePetEntity() {
        //given
        final Pet pet = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Pet.class);
        //when
        petSaveService.savePetEntity(pet);
        //then
        then(petRepository).should().save(pet);
    }

}