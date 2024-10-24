package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("John Doe");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setIsbn("1234567890");
        book.setAuthor(author);
        book.setAvailableCopies(5);
    }

    @Test
    void whenGetAllBooks_thenReturnBookList() {
        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor(author);
        book2.setAvailableCopies(5);

        List<Book> books = Arrays.asList(book, book2);

        when(bookRepository.findAllWithAuthors()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Book::getTitle).containsExactlyInAnyOrder("Test Book", "Book 2");
        assertThat(result).extracting(b -> b.getAuthor().getName()).containsOnly("John Doe");
    }

    @Test
    void whenSaveBook_thenReturnSavedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        assertThat(savedBook.getIsbn()).isEqualTo("1234567890");
        assertThat(savedBook.getAuthor().getName()).isEqualTo("John Doe");
    }

    @Test
    void whenGetBookById_thenReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Book");
        assertThat(result.getAuthor().getName()).isEqualTo("John Doe");
    }

    @Test
    void whenUpdateBook_thenReturnUpdatedBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = bookService.updateBook(book);

        assertThat(updatedBook.getTitle()).isEqualTo("Test Book");
        assertThat(updatedBook.getIsbn()).isEqualTo("1234567890");
        assertThat(updatedBook.getAuthor().getName()).isEqualTo("John Doe");
        verify(bookRepository, times(1)).save(book);
    }
}
