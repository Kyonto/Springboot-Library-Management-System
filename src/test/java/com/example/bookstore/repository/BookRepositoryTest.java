package com.example.bookstore.repository;

import com.example.bookstore.BookstoreApplication;
import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = BookstoreApplication.class)
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void whenFindAllWithAuthors_thenReturnBookListWithAuthors() {
        Author author = new Author("John Doe", "john@example.com");
        entityManager.persist(author);

        Book book1 = new Book("Book 1", "1234567890", author, 5);
        entityManager.persist(book1);

        Book book2 = new Book("Book 2", "0987654321", author, 5);
        entityManager.persist(book2);

        entityManager.flush();

        List<Book> books = bookRepository.findAllWithAuthors();

        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
        assertThat(books).extracting(book -> book.getAuthor().getName()).containsOnly("John Doe");
    }

    @Test
    void whenFindByIsbn_thenReturnBook() {
        Author author = new Author("John Doe", "john@example.com");
        entityManager.persist(author);

        Book book = new Book("Test Book", "1234567890", author, 5);
        entityManager.persist(book);

        entityManager.flush();

        Book found = bookRepository.findByIsbn("1234567890");

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test Book");
        assertThat(found.getAuthor().getName()).isEqualTo("John Doe");
    }
}
