package com.pawith.todoapplication.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoCreateRequest {
    private Long categoryId;
    private Long todoTeamId;
    private String description;
    private LocalDate scheduledDate;
    private List<Long> registerIds;
}
