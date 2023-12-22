package com.pawith.event.christmas;

import com.pawith.commonmodule.schedule.AbstractBatchSchedulingHandler;
import com.pawith.event.christmas.entity.ChristmasCategory;
import com.pawith.event.christmas.repository.ChristmasCategoryRepository;
import com.pawith.tododomain.repository.AssignRepository;
import com.pawith.tododomain.repository.CategoryRepository;
import com.pawith.tododomain.repository.TodoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ChristmasEventCloseHandler extends AbstractBatchSchedulingHandler<ChristmasCategory> {
    private static final Integer BATCH_SIZE = 100;
        private static final String CRON_EXPRESSION = "0 0 0 26 12 *";

    private final ChristmasCategoryRepository christmasCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final AssignRepository assignRepository;

    public ChristmasEventCloseHandler(ChristmasCategoryRepository christmasCategoryRepository, CategoryRepository categoryRepository, TodoRepository todoRepository, AssignRepository assignRepository) {
        super(BATCH_SIZE, CRON_EXPRESSION);
        this.christmasCategoryRepository = christmasCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.todoRepository = todoRepository;
        this.assignRepository = assignRepository;
    }


    @Override
    protected List<ChristmasCategory> extractBatchData(Pageable pageable) {
        return christmasCategoryRepository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    protected void processBatch(List<ChristmasCategory> executionResult) {
        executionResult
            .forEach(christmasCategory -> {
                final Long categoryId = christmasCategory.getCategoryId();
                christmasCategoryRepository.deleteById(christmasCategory.getId());
                assignRepository.deleteAllByCategoryIdQuery(categoryId);
                todoRepository.deleteAllByCategoryIdQuery(categoryId);
                categoryRepository.deleteById(categoryId);
            });
    }
}
