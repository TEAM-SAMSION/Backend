package com.pawith.todoapplication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class TodoCreateRequest {
    private Long categoryId;
    private Long todoTeamId;
    private String description;
    private LocalDate scheduledDate;
    private List<Long> registerIds;
}
