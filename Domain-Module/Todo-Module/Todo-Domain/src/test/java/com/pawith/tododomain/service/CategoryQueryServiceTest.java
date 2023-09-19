package com.pawith.tododomain.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("CategoryQueryService 테스트")
class CategoryQueryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryQueryService categoryQueryService;

    @BeforeEach
    void init() {
        categoryQueryService = new CategoryQueryService(categoryRepository);
    }

    @Test
    @DisplayName("todoTeamId를 받아 해당 팀의 카테고리 목록을 조회한다.")
    void findCategoryListByTodoTeamId() {
        //given
        final Long mockTodoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<Category> mockCategoryList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey().giveMe(Category.class, 10);
        given(categoryRepository.findAllByTodoTeamId(mockTodoTeamId)).willReturn(mockCategoryList);
        //when
        List<Category> result = categoryQueryService.findCategoryListByTodoTeamId(mockTodoTeamId);
        //then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockCategoryList);
    }

}