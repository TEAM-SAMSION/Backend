package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.Alarm;
import com.pawith.commonmodule.domain.BatchInsertRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AlarmBatchRepository extends BatchInsertRepository<Alarm> {
}
