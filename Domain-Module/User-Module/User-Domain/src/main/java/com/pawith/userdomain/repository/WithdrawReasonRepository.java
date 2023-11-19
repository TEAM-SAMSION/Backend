package com.pawith.userdomain.repository;

import com.pawith.userdomain.entity.WithdrawReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawReasonRepository extends JpaRepository<WithdrawReason, Long> {
}
