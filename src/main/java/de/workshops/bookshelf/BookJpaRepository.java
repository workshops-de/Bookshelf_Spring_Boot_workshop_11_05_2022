package de.workshops.bookshelf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookJpaRepository extends JpaRepository<Book, Long>, CustomBookJpaRepository {
    Book findByIsbn(String isbn);
}

interface CustomBookJpaRepository {
    List<Book> findBookBySearchParameter(BookSearchParameter searchParameter);
}

class CustomBookJpaRepositoryImpl implements CustomBookJpaRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomBookJpaRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findBookBySearchParameter(BookSearchParameter searchParameter) {
        // use JdbcTemplate methods to find books
        return List.of();
    }
}