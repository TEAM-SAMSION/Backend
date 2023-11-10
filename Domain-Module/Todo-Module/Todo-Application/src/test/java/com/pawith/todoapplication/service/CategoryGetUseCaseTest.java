package com.pawith.todoapplication.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.pawith.commonmodule.UnitTestConfig;
import com.pawith.commonmodule.response.ListResponse;
import com.pawith.commonmodule.utils.FixtureMonkeyUtils;
import com.pawith.todoapplication.dto.response.CategoryInfoResponse;
import com.pawith.tododomain.entity.Category;
import com.pawith.tododomain.service.CategoryQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.BDDMockito.given;

@UnitTestConfig
@DisplayName("CategoryGetUseCase 테스트")
public class CategoryGetUseCaseTest {

    @Mock
    private CategoryQueryService categoryQueryService;
    private CategoryGetUseCase categoryGetUseCase;

    @BeforeEach
    void init(){
        categoryGetUseCase = new CategoryGetUseCase(categoryQueryService);
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    void getCategoryList() {
        // given
        final Long TodoTeamId = FixtureMonkey.create().giveMeOne(Long.class);
        final List<Category> categoryList = FixtureMonkeyUtils.getReflectionbasedFixtureMonkey()
            .giveMe(Category.class, 5);
        given(categoryQueryService.findCategoryListByTodoTeamIdAndStatus(TodoTeamId)).willReturn(categoryList);
        // when
        final ListResponse<CategoryInfoResponse> response = categoryGetUseCase.getCategoryList(TodoTeamId);
        // then
        Assertions.assertThat(response.getContent().size()).isEqualTo(categoryList.size());
    }


}
