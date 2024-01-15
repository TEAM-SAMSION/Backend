package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.repository.AssignBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class AssignBatchRepositoryImpl implements AssignBatchRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void batchInsert(Collection<Assign> entities) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO assign (todo_id, register_id, completion_status, is_deleted, created_at, updated_at) VALUES (?, ?, ?, false, now(), now())",
                entities,
                1000,
                (ps, assign) -> {
                    ps.setLong(1, assign.getTodo().getId());
                    ps.setLong(2, assign.getRegister().getId());
                    ps.setString(3, assign.getCompletionStatus().toString());
                }
        );

    }
}
