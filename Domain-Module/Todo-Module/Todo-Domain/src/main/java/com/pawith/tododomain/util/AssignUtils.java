package com.pawith.tododomain.util;

import com.pawith.tododomain.entity.Assign;
import com.pawith.tododomain.entity.Todo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssignUtils {

    public static Map<Todo, List<Assign>> convertToTodoAssignMap(final List<Assign> assigns) {
        return assigns.stream()
            .collect(Collectors.groupingBy(Assign::getTodo, LinkedHashMap::new, Collectors.toList()));
    }
}
