package com.pawith.todoinfrastructure.dao;

import com.pawith.tododomain.repository.dao.IncompleteAssignInfoDao;
import com.querydsl.core.annotations.QueryProjection;

public record IncompleteAssignInfoDaoImpl(
    Long userId,
    String todoTeamName,
    String todoDescription
) implements IncompleteAssignInfoDao {

    @QueryProjection
    public IncompleteAssignInfoDaoImpl {
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public String getTodoTeamName() {
        return todoTeamName;
    }

    @Override
    public String getTodoDescription() {
        return todoDescription;
    }
}
