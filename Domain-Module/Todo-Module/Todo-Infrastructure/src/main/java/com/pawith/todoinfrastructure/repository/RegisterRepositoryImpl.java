package com.pawith.todoinfrastructure.repository;

import com.pawith.commonmodule.util.SliceUtils;
import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.repository.RegisterQueryRepository;
import com.pawith.todoinfrastructure.dao.IncompleteTodoCountInfoDaoImpl;
import com.pawith.todoinfrastructure.dao.QIncompleteTodoCountInfoDaoImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RegisterRepositoryImpl implements RegisterQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Register> findAllByUserIdQuery(Long userId, Pageable pageable) {
        QRegister qRegister = QRegister.register;
        QTodoTeam qTodoTeam = qRegister.todoTeam;
        List<Register> registers = queryFactory.select(qRegister)
            .from(qRegister)
            .join(qTodoTeam).fetchJoin()
            .where(qRegister.userId.eq(userId).and(qRegister.isRegistered.isTrue()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();
        return SliceUtils.getSliceImpl(registers, pageable);
    }

    @Override
    public List<Register> findAllByIdsQuery(List<Long> ids) {
        QRegister qRegister = QRegister.register;
        return queryFactory.select(qRegister)
            .from(qRegister)
            .where(qRegister.id.in(ids))
            .fetch();
    }

    @Override
    public List<Register> findAllByUserIdWithTodoTeamFetchQuery(Long userId) {
        QRegister qRegister = QRegister.register;
        QTodoTeam qTodoTeam = qRegister.todoTeam;
        return queryFactory.select(qRegister)
            .from(qRegister)
            .join(qTodoTeam).fetchJoin()
            .where(qRegister.userId.eq(userId))
            .orderBy(qRegister.registerAt.desc())
            .fetch();
    }

    @Override
    public Integer countByTodoTeamIdQuery(Long todoTeamId) {
        QRegister qRegister = QRegister.register;
        return queryFactory.select(qRegister.count())
            .from(qRegister)
            .where(qRegister.todoTeam.id.eq(todoTeamId)
                .and(qRegister.isRegistered.eq(true)))
            .fetchOne().intValue();
    }

    @Override
    public Optional<Register> findLatestRegisterByUserIdQuery(Long userId) {
        QRegister qRegister = QRegister.register;
        QTodoTeam qTodoTeam = qRegister.todoTeam;
        return Optional.ofNullable(queryFactory.select(qRegister)
            .from(qRegister)
            .join(qTodoTeam).fetchJoin()
            .where(qRegister.userId.eq(userId))
                .orderBy(qRegister.id.desc())
                .limit(1)
            .fetchOne());
    }

    @Override
    public void deleteByRegisterIdsQuery(List<Long> registerIds) {
        QRegister qRegister = QRegister.register;
        queryFactory.update(qRegister)
            .set(qRegister.isDeleted, true)
            .set(qRegister.isRegistered, false)
            .where(qRegister.id.in(registerIds))
            .execute();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<IncompleteTodoCountInfoDaoImpl> findAllIncompleteTodoCountInfoQuery(Pageable pageable) {
        final QRegister qRegister = QRegister.register;
        final QTodoTeam qTodoTeam = qRegister.todoTeam;
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = qAssign.todo;
        return queryFactory.select(new QIncompleteTodoCountInfoDaoImpl(qTodoTeam.id, qRegister.userId, qTodoTeam.teamName, qAssign.count()))
            .from(qRegister)
            .join(qTodoTeam)
            .join(qAssign).on(qAssign.register.eq(qRegister))
            .join(qTodo).on(qTodo.scheduledDate.eq(LocalDate.now()))
            .where(qAssign.completionStatus.eq(CompletionStatus.INCOMPLETE).and(qRegister.isRegistered.isTrue()))
            .groupBy(qRegister.userId, qTodoTeam.teamName)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }

}
