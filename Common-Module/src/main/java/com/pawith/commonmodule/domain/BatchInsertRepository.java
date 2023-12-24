package com.pawith.commonmodule.domain;

import java.util.Collection;

public interface BatchInsertRepository<T> {
    void batchInsert(Collection<T> entities);
}
