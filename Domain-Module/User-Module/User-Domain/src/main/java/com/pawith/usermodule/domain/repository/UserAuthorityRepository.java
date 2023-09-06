package com.pawith.usermodule.domain.repository;

import com.pawith.usermodule.domain.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    Optional<UserAuthority> findByEmail(String email);
}
