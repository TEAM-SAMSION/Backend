package com.pawith.alarmmodule.repository;

import com.pawith.alarmmodule.entity.Alarm;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface AlarmBatchRepository{
    void saveAllBatch(Collection<Alarm> entities);
}
