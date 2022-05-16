package de.workshops.bookshelf;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.equalTo;

@SpringBootTest
class BookRestControllerWithRestAssuredStandaloneTest {
    @Autowired
    BookRestController bookRestController;

    @Test
    void getAllBooksRestAssuredStandalone() {
        RestAssuredMockMvc.standaloneSetup(bookRestController);
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