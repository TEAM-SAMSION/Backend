package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.QAssign;
import com.pawith.tododomain.entity.QRegister;
import com.pawith.tododomain.entity.QTodo;
import com.pawith.tododomain.repository.AssignQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AssignRepositoryImpl implements AssignQueryRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Assign> findAllByCategoryIdAndScheduledDateQuery(Long categoryId, LocalDate scheduledDate) {
        final QAssign qAssign = QAssign.assign;
        final QTodo qTodo = qAssign.todo;
        final QRegister qRegister = qAssign.register;
        return queryFactory.select(qAssign)
            .from(qAssign)
            .join(qTodo).fetchJoin()
            .join(qRegister).fetchJoin()
            .where(qTodo.category.id.eq(categoryId)
                .and(qTodo.scheduledDate.eq(scheduledDate)))
            .orderBy(qTodo.completionStatus.desc())
            .fetch();
    }
}
