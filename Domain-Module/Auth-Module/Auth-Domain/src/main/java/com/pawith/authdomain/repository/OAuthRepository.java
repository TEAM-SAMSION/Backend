package com.pawith.authdomain.repository;


import com.pawith.authdomain.entity.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuthRepository extends JpaRepository<OAuth, Long> {
    boolean existsBySub(String sub);

    Optional<OAuth> findBySub(String sub);

    Optional<OAuth> findByUserId(Long userId);
}
