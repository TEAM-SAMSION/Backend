package com.pawith.todoinfrastructure.repository;

import com.pawith.commonmodule.util.SliceUtils;
import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.QAssign;
import com.pawith.tododomain.entity.QCategory;
import com.pawith.tododomain.entity.QRegister;
import com.pawith.tododomain.entity.QTodoTeam;
import com.pawith.tododomain.entity.Register;
import com.pawith.tododomain.repository.RegisterQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

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
            .where(qRegister.userId.eq(userId)
                .and(qRegister.isRegistered.eq(true)))
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
    public List<Register> findByTodoIdQuery(Long todoId) {
        QRegister qRegister = QRegister.register;
        QAssign qAssign = QAssign.assign;
        return queryFactory.select(qRegister)
            .from(qRegister)
            .join(qAssign).on(qAssign.id.eq(todoId))
            .where(qAssign.register.id.eq(qRegister.id))
            .fetch();
    }

    @Override
    public List<Register> findAllByCategoryIdQuery(Long categoryId) {
        QRegister qRegister = QRegister.register;
        QCategory qCategory = QCategory.category;
        return queryFactory.select(qRegister)
            .from(qRegister)
            .join(qCategory).on(qCategory.id.eq(categoryId))
            .where(qCategory.todoTeam.id.eq(qRegister.todoTeam.id))
            .fetch();
    }

    @Override
    public List<Register> findAllByUserIdWithTodoTeamFetchQuery(Long userId) {
        QRegister qRegister = QRegister.register;
        QTodoTeam qTodoTeam = qRegister.todoTeam;
        return queryFactory.select(qRegister)
            .from(qRegister)
            .join(qTodoTeam).fetchJoin()
            .where(qRegister.userId.eq(userId)
                .and(qRegister.isRegistered.eq(true)))
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
    public Integer countByTodoTeamIdAndAuthorityQuery(Long todoTeamId, Authority authority) {
        QRegister qRegister = QRegister.register;
        return queryFactory.select(qRegister.count())
            .from(qRegister)
            .where(qRegister.todoTeam.id.eq(todoTeamId)
                .and(qRegister.authority.eq(authority))
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
            .where(qRegister.userId.eq(userId)
                .and(qRegister.isRegistered.isTrue()))
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

}
