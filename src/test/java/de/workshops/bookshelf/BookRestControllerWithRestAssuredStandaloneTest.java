package de.workshops.bookshelf;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest
class BookRestControllerWithRestAssuredStandaloneTest {
    @Autowired
    BookRestController bookRestController;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Test
    @WithMockUser
    void getAllBooksRestAssuredStandalone() {
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders
                .standaloneSetup(bookRestController)
                .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain)));
        RestAssuredMockMvc.given().
                log().all().
                when().
                get("/books").
                then().
                log().all().
                statusCode(200).
                body("author[0]", equalTo("Erich Gamma"));
    }
}