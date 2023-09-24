package com.pawith.todoapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class TodoCreateRequest {
    private Long categoryId;
    private String description;
    private LocalDate scheduledDate;
    private List<Long> registerIds;
}
