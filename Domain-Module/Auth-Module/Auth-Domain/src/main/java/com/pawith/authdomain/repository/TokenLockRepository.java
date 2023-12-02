package com.pawith.authdomain.repository;

import com.pawith.authdomain.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenLockRepository extends JpaRepository<Token, Long> {

    @Query(value = "SELECT GET_LOCK(:value, 1000)", nativeQuery = true)
    void getNamedLock(@Param("value") String value);

    @Query(value = "SELECT RELEASE_LOCK(:value)", nativeQuery = true)
    void releaseNamedLock(@Param("value") String value);
}
