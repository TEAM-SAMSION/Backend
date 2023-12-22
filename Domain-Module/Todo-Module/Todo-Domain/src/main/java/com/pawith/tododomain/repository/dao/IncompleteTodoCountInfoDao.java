package com.pawith.tododomain.repository.dao;

public interface IncompleteTodoCountInfoDao {
    Long getTodoTeamId();
    Long getUserId();
    String getTodoTeamName();
    Long getIncompleteTodoCount();
}
