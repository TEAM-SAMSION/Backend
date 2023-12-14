package com.pawith.todoapplication.service;

import static org.mockito.BDDMockito.given;

import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.request.CategoryNameChageRequest;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.entity.CategoryStatus;
import com.pawith.tododomain.service.CategoryQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@UnitTestConfig
@DisplayName("CategoryChangeUseCase 테스트")
public class CategoryChangeUseCaseTest {

    @Mock
    private CategoryQueryService categoryQueryService;

    private CategoryChangeUseCase categoryChangeUseCase;

    @BeforeEach
    void init() {
        categoryChangeUseCase = new CategoryChangeUseCase(categoryQueryService);
    }

    @Test
    @DisplayName("카테고리 상태가 ON 일때 OFF 로 변경 테스트")
    void changeCategoryStatusOn() {
        //given
        Long categoryId = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Long.class);
        Category category = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Category.class)
                        .set("categorystatus", CategoryStatus.ON)
                        .sample();
        given(categoryQueryService.findCategoryByCategoryId(categoryId)).willReturn(category);
        //when
        categoryChangeUseCase.changeCategoryStatus(categoryId);
        //then
        Assertions.assertThat(category.getCategoryStatus()).isEqualTo(CategoryStatus.OFF);
    }

    @Test
    @DisplayName("카테고리 상태가 OFF 일때 ON 로 변경 테스트")
    void changeCategoryStatusOff() {
        //given
        Long categoryId = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Long.class);
        Category category = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Category.class)
                        .set("categorystatus", CategoryStatus.OFF)
                        .sample();
        given(categoryQueryService.findCategoryByCategoryId(categoryId)).willReturn(category);
        //when
        categoryChangeUseCase.changeCategoryStatus(categoryId);
        //then
        Assertions.assertThat(category.getCategoryStatus()).isEqualTo(CategoryStatus.ON);
    }

    @Test
    @DisplayName("카테고리 이름 변경 테스트")
    void changeCategoryName() {
        //given
        Long categoryId = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(Long.class);
        CategoryNameChageRequest categoryNameChageRequest = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(CategoryNameChageRequest.class);
        String categoryName = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeOne(String.class);
        Category category = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMeBuilder(Category.class)
                        .set("categoryName", categoryName)
                        .sample();
        given(categoryQueryService.findCategoryByCategoryId(categoryId)).willReturn(category);
        //when
        categoryChangeUseCase.changeCategoryName(categoryId, categoryNameChageRequest);
        //then
        Assertions.assertThat(category.getName()).isEqualTo(categoryNameChageRequest.getCategoryName());
    }

}
