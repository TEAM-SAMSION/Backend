package com.pawith.todoapplication.service;

import com.pawith.commonmodule.annotation.ApplicationService;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.todoapplication.dto.response.PetInfoResponse;
import com.pawith.tododomain.entity.Pet;
import com.pawith.tododomain.service.PetQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetGetUseCase {
    private final PetQueryService petQueryService;

    public ListResponse<PetInfoResponse> getTodoTeamPets(Long todoTeamId) {
        List<Pet> pets = petQueryService.findAllByTodoTeamId(todoTeamId);
        return ListResponse.from(
                pets.stream()
                        .map(pet -> new PetInfoResponse(pet.getId(), pet.getImageUrl(), pet.getName(), pet.getAge(),
                                pet.getPetSpecies().getGenus(), pet.getPetSpecies().getSpecies(),pet.getDescription()))
                        .collect(Collectors.toList())
        );
    }
}
