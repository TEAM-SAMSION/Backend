package com.pawith.commonmodule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureRestDocs
@Import({RestDocsConfig.class})
@ExtendWith(RestDocumentationExtension.class)
public class BaseRestDocsTest {

    @Autowired
    protected RestDocumentationResultHandler resultHandler;

    protected MockMvc mvc;

    @BeforeEach
    void setUp(final WebApplicationContext applicationContext,
               final RestDocumentationContextProvider provider){
        this.mvc = MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply(MockMvcRestDocumentation.documentationConfiguration(provider).uris().withPort(8080))
            .alwaysDo(MockMvcResultHandlers.print())
            .alwaysDo(resultHandler)
//            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .build();
    }

}
