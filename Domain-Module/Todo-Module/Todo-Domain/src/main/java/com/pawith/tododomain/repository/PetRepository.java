package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Integer countByTodoTeamId(Long todoTeamId);
    List<Pet> findAllByTodoTeamId(Long todoTeamId);
}
