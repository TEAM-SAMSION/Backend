package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, Long> {
}
