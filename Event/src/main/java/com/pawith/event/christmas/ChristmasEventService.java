package com.pawith.event.christmas;

import com.pawith.event.christmas.entity.ChristmasCategory;
import com.pawith.event.christmas.repository.ChristmasCategoryRepository;
import com.pawith.tododomain.entity.*;
import com.pawith.tododomain.repository.*;
import com.pawith.tododomain.service.RegisterQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChristmasEventService {
    private static final String EVENT_CATEGORY_NAME = "크리스마스";
    private static final String EVENT_TODO_DESCRIPTION = "포잇스마스 이벤트 참여하기";
    private static final List<LocalDate> EVENT_TODO_SCHEDULED_DATE_LIST = List.of(
        LocalDate.of(2023, 12, 23),
        LocalDate.of(2023, 12, 24),
        LocalDate.of(2023, 12, 25)
    );


    private static final Long EVENT_CREATOR_ID = -1L;
    private final CategoryRepository categoryRepository;
    private final ChristmasCategoryRepository christmasCategoryRepository;
    private final RegisterRepository registerRepository;
    private final TodoRepository todoRepository;
    private final AssignRepository assignRepository;
    private final RegisterQueryService registerQueryService;

    @Transactional
    public void publishEvent(TodoTeam todoTeam){
        final Category category = Category.builder()
            .todoTeam(todoTeam)
            .name(EVENT_CATEGORY_NAME)
            .categoryStatus(CategoryStatus.ON)
            .build();
        final Category saved = categoryRepository.save(category);
        final ChristmasCategory christmasCategory = new ChristmasCategory(saved.getId());
        christmasCategoryRepository.save(christmasCategory);

        final List<Register> allRegisters = registerRepository.findAllByTodoTeamId(todoTeam.getId());
        final List<Todo> eventTodoList = new ArrayList<>();
        final List<Assign> eventAssignList = new ArrayList<>();
        EVENT_TODO_SCHEDULED_DATE_LIST
            .forEach(eventDate -> {
                    final Todo todo = Todo.builder()
                        .category(saved)
                        .creatorId(EVENT_CREATOR_ID)
                        .description(EVENT_TODO_DESCRIPTION)
                        .scheduledDate(eventDate)
                        .build();
                    eventTodoList.add(todo);
                    final List<Assign> assignList = allRegisters.stream()
                        .filter(Register::isRegistered)
                        .map(register -> Assign.builder()
                            .todo(todo)
                            .register(register)
                            .build()).toList();
                    eventAssignList.addAll(assignList);
                }
            );
        todoRepository.saveAll(eventTodoList);
        assignRepository.saveAll(eventAssignList);
    }

    @Transactional
    public void addNewRegisterAtChristmasEventTodo(Long todoTeamId, Long userId){
        final Register register = registerQueryService.findRegisterByTodoTeamIdAndUserId(todoTeamId, userId);
        final TodoTeam todoTeam = register.getTodoTeam();
        todoRepository.findTodoListByCreatorIdAndTodoTeamIdQuery(EVENT_CREATOR_ID, todoTeam.getId())
            .forEach(todo -> {
                final Assign assign = Assign.builder()
                    .todo(todo)
                    .register(register)
                    .build();
                assignRepository.save(assign);
            });
    }

    public Boolean isEventDay(){
        return EVENT_TODO_SCHEDULED_DATE_LIST.contains(LocalDate.now());
    }

}
