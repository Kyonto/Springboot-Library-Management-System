package com.example.bookstore.service;

import com.example.bookstore.model.Author;
import com.example.bookstore.repository.AuthorRepository;
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
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setEmail("john@example.com");
    }

    @Test
    void whenGetAllAuthors_thenReturnAuthorList() {
        // given
        Author author2 = new Author();
        author2.setName("Jane Doe");
        List<Author> authors = Arrays.asList(author, author2);

        when(authorRepository.findAll()).thenReturn(authors);

        // when
        List<Author> result = authorService.getAllAuthors();

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Author::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    void whenSaveAuthor_thenReturnSavedAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.saveAuthor(author);

        assertThat(savedAuthor.getName()).isEqualTo("John Doe");
        assertThat(savedAuthor.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void whenGetAuthorById_thenReturnAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Doe");
    }

    @Test
    void whenUpdateAuthor_thenReturnUpdatedAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author updatedAuthor = authorService.updateAuthor(author);

        assertThat(updatedAuthor.getName()).isEqualTo("John Doe");
        assertThat(updatedAuthor.getEmail()).isEqualTo("john@example.com");
        verify(authorRepository, times(1)).save(author);
    }
}
