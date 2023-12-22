package com.pawith.todoinfrastructure.repository;

import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.CategoryStatus;
import com.pawith.tododomain.entity.QCategory;
import com.pawith.tododomain.repository.CategoryQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Category> findAllByTodoTeamIdAndCategoryStatusQuery(Long todoTeamId, LocalDate moveDate) {
        final QCategory qCategory = QCategory.category;
        return queryFactory.select(qCategory)
            .from(qCategory)
            .where(
                qCategory.todoTeam.id.eq(todoTeamId)
                  .and(
                     qCategory.categoryStatus.eq(CategoryStatus.ON)
                       .or(
                            moveDate != null
                            ? qCategory.categoryStatus.eq(CategoryStatus.OFF)
                            .and(qCategory.disabledAt.gt(moveDate))
                            : null
                           )
                    )
                )
            .orderBy(qCategory.createdAt.desc())
            .fetch();
    }

    @Override
    public List<Category> findAllByTodoTeamIdQuery(Long todoTeamId) {
        final QCategory qCategory = QCategory.category;
        return queryFactory.select(qCategory)
            .from(qCategory)
            .where(qCategory.todoTeam.id.eq(todoTeamId))
            .orderBy(qCategory.createdAt.desc())
            .fetch();
    }
}
