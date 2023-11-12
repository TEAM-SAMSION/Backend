package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.Alarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("select count(a) > 0 " +
        "from Alarm a " +
        "join AlarmUser au on au.userId = :userId and a.alarmUser = au "+
        "where a.isRead = false")
    Boolean existsByUserId(Long userId);

    @Query("select a " +
        "from Alarm a " +
        "join AlarmUser au on au.userId = :userId and a.alarmUser = au ")
    Slice<Alarm> findAllByUserId(Long userId, Pageable pageable);
}
