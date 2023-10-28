package com.pawith.userdomain.repository;

import com.pawith.userdomain.entity.PathHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PathHistoryRepository extends JpaRepository<PathHistory, Long> {
}
