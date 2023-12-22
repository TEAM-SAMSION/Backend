package com.pawith.todoinfrastructure.dao;

import com.pawith.tododomain.repository.dao.IncompleteTodoCountInfoDao;
import com.querydsl.core.annotations.QueryProjection;

public record IncompleteTodoCountInfoDaoImpl(Long todoTeamId, Long userId, String todoTeamName, Long incompleteTodoCount) implements IncompleteTodoCountInfoDao {

    @QueryProjection
    public IncompleteTodoCountInfoDaoImpl {
    }

    @Override
    public Long getTodoTeamId() {
        return this.todoTeamId;
    }

    @Override
    public Long getUserId() {
        return this.userId;
    }

    @Override
    public String getTodoTeamName() {
        return this.todoTeamName;
    }

    @Override
    public Long getIncompleteTodoCount() {
        return this.incompleteTodoCount;
    }
}
