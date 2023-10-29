package com.pawith.tododomain.service;

import com.pawith.commonmodule.annotation.DomainService;
import com.pawith.tododomain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional
public class CategoryDeleteService {

    private final CategoryRepository categoryRepository;

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
