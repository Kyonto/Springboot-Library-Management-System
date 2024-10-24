package com.example.bookstore.repository;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = BookstoreApplication.class)
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void whenFindById_thenReturnAuthor() {
        Author author = new Author("John Doe", "john@example.com");
        entityManager.persist(author);
        entityManager.flush();

        Optional<Author> found = authorRepository.findById(author.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo(author.getName());
    }

    @Test
    void whenFindAll_thenReturnAuthorList() {
        Author author1 = new Author("John Doe", "john@example.com");
        entityManager.persist(author1);

        Author author2 = new Author("Jane Doe", "jane@example.com");
        entityManager.persist(author2);

        entityManager.flush();

        List<Author> authors = authorRepository.findAll();

        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    void whenFindByEmail_thenReturnAuthor() {
        Author author = new Author("John Doe", "john@example.com");
        entityManager.persist(author);
        entityManager.flush();

        Optional<Author> found = authorRepository.findByEmail("john@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("John Doe");
    }
}
