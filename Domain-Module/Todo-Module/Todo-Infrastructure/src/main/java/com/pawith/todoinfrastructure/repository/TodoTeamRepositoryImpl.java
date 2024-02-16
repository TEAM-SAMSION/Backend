package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.QAssign;
import com.pawith.tododomain.entity.QRegister;
import com.pawith.tododomain.entity.QTodoTeam;
import com.pawith.tododomain.entity.TodoTeam;
import com.pawith.tododomain.repository.TodoTeamQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoTeamRepositoryImpl implements TodoTeamQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TodoTeam> findAllByUserIdQuery(Long userId) {
        final QTodoTeam todoTeam = QTodoTeam.todoTeam;
        final QRegister register = QRegister.register;
        return jpaQueryFactory.select(todoTeam)
                .from(todoTeam)
                .join(register).on(register.userId.eq(userId))
                .where(register.todoTeam.eq(todoTeam)
                        .and(register.isRegistered.eq(true)))
                .fetch();
    }

    @Override
    public Optional<TodoTeam> findByTodoId(Long todoId) {
        final QTodoTeam todoTeam = QTodoTeam.todoTeam;
        final QRegister register = QRegister.register;
        final QAssign assign = QAssign.assign;
        return Optional.ofNullable(
                jpaQueryFactory.select(todoTeam)
                        .from(todoTeam)
                        .join(register).on(register.todoTeam.eq(todoTeam))
                        .join(assign).on(assign.register.eq(register))
                        .where(assign.todo.id.eq(todoId))
                        .fetchOne()
        );
    }

}
