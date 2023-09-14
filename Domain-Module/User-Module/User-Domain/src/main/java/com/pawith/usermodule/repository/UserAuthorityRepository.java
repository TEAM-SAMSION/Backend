package com.pawith.usermodule.repository;

import com.pawith.usermodule.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

    Optional<UserAuthority> findByEmail(String email);
}
