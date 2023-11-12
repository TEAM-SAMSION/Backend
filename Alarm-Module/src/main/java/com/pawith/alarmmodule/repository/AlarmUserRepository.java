package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.AlarmUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmUserRepository extends JpaRepository<AlarmUser, Long> {
    Optional<AlarmUser> findByUserId(Long userId);
}
