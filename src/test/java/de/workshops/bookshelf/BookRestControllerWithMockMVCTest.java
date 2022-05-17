package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(properties = "greeting=Hallo du da")
@TestPropertySource(locations = "classpath:custom.properties")
@Import(TestConfig.class)
@ActiveProfiles({"test", "prod"})
class BookRestControllerWithMockMVCTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookRestControllerWithMockMVCTest.class);
    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Value("${greeting}")
    String greeting;
    @Test
    void getAllBooks() throws Exception {
        LOGGER.error("Greeting: {}", greeting);
        when(bookService.getAllBooks()).thenReturn(List.of(new Book()));

        final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        final var contentAsString = mvcResult.getResponse().getContentAsString();
        LOGGER.info("contentAsString: {}", contentAsString);

        List<Book> books = mapper.readValue(contentAsString, new TypeReference<>() {});

        assertThat(books).hasSize(1);
    }

//    @TestConfiguration
//    static class InnerClassTestConfig {
//        @Bean
//        public ObjectMapper mapper() {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//            return mapper;
//        }
//    }
}