package com.pawith.tododomain.repository;

import com.pawith.tododomain.entity.TodoNotification;
import com.pawith.tododomain.repository.dao.NotificationDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface TodoNotificationRepository extends JpaRepository<TodoNotification, Long> {

    @Query("select c.todoTeam.id as todoTeamId ,r.userId as userId, c.name as categoryName, t.description as todoDescription, tn.notificationTime as notificationTime " +
        "from TodoNotification tn " +
        "join fetch Assign a on tn.assign = a and a.completionStatus='INCOMPLETE' " +
        "join fetch Todo t on a.todo = t and t.scheduledDate = date(now()) " +
        "join fetch Category c on t.category = c "+
        "join fetch Register r on a.register = r and r.isRegistered = true "+
        "where (tn.notificationTime-current_time) <= :criterionTime " +
        "and timediff(tn.notificationTime, current_time) > 0")
    Slice<NotificationDao> findAllWithNotCompletedAssignAndTodayScheduledTodo(Duration criterionTime, Pageable pageable);

    @Query(value = """
        select tn
        from TodoNotification tn
            join Assign a on tn.assign = a and a.completionStatus='INCOMPLETE'
            join Register r on a.register = r and r.isRegistered = true and r.userId = :userId
        where a.todo.id in :todoId
        """)
    List<TodoNotification> findAllByTodoIdWithIncompleteAssign(List<Long> todoId, Long userId);


    Optional<TodoNotification> findByAssignId(Long assignId);
}
