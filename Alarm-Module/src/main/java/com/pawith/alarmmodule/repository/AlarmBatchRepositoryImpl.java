package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
@RequiredArgsConstructor
public class AlarmBatchRepositoryImpl implements AlarmBatchRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllBatch(Collection<Alarm> entities) {
        final String insertSql = """
            INSERT INTO alarm (domain_id, message, alarm_category,is_read,alarm_user_id,created_at, updated_at)
            VALUES (?, ?, ?, ?, ?,now(),now())
            """;

        jdbcTemplate.batchUpdate(insertSql, entities, 1000, (ps, alarm) -> {
            ps.setLong(1, alarm.getAlarmBody().getDomainId());
            ps.setString(2, alarm.getAlarmBody().getMessage());
            ps.setString(3, alarm.getAlarmCategory().toString());
            ps.setBoolean(4, alarm.getIsRead());
            ps.setLong(5, alarm.getAlarmUser().getId());
        });
    }
}
