package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.Alarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    @Query("""
        select count(a)
        from Alarm a
        join AlarmUser au on au.userId = :userId and a.alarmUser = au
        where a.isRead = false
        """)
    Integer countUnReadAlarm(Long userId);

    @Query(
        """
                    select a
                    from Alarm a
                        join AlarmUser au on au.userId = :userId and a.alarmUser = au
                    order by a.id desc
            """
    )
    Slice<Alarm> findAllByUserId(Long userId, Pageable pageable);


    @Modifying
    @Query("""
            update Alarm a
            set a.isRead = true
            where a.isRead = false and a.alarmUser in (select au from AlarmUser au where au.userId = :userId)
        """)
    void changeAllAlarmStatusToRead(Long userId);
}
