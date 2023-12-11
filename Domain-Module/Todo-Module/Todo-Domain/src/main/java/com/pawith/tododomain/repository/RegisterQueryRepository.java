package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.repository.dao.IncompleteTodoCountInfoDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface RegisterQueryRepository {
    Slice<Register> findAllByUserIdQuery(Long userId, Pageable pageable);
    List<Register> findAllByIdsQuery(List<Long> ids);
    List<Register> findByTodoIdQuery(Long todoId);
    List<Register> findAllByCategoryIdQuery(Long categoryId);
    List<Register> findAllByUserIdWithTodoTeamFetchQuery(Long userId);
    Integer countByTodoTeamIdQuery(Long todoTeamId);
    Integer countByTodoTeamIdAndAuthorityQuery(Long todoTeamId, Authority authority);
    Optional<Register> findLatestRegisterByUserIdQuery(Long userId);
    void deleteByRegisterIdsQuery(List<Long> registerIds);
    <T extends IncompleteTodoCountInfoDao> List<T> findAllIncompleteTodoCountInfoQuery(Pageable pageable);
}
