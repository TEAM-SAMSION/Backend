package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Integer countByTodoTeamId(Long todoTeamId);
}
