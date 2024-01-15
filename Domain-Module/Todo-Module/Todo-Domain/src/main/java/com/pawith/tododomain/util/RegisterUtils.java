package com.pawith.tododomain.util;

import com.pawith.tododomain.entity.Authority;
import com.pawith.tododomain.entity.Register;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterUtils {

    public static List<Long> extractUserIds(Collection<Register> registers) {
        return registers.stream()
            .map(Register::getUserId)
            .toList();
    }

    public static Map<Long, Register> convertToMapWithUserIdKey(List<Register> registers) {
        return registers.stream()
            .map(register -> Map.entry(register.getUserId(), register))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<Register> sortByAuthority(List<Register> registers) {
        final Comparator<Authority> authorityComparator = Comparator.comparing(Enum::ordinal);
        return registers.stream()
            .sorted(Comparator.comparing(Register::getAuthority, authorityComparator))
            .toList();
    }


}
