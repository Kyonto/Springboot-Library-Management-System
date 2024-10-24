package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void whenGetAuthors_thenReturnAuthorsList() throws Exception {
        List<Author> authors = Arrays.asList(
            new Author(1L, "John Doe", "john@example.com"),
            new Author(2L, "Jane Doe", "jane@example.com")
        );
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/authors"))
               .andExpect(status().isOk())
               .andExpect(view().name("author/list"))
               .andExpect(model().attributeExists("authors"))
               .andExpect(model().attribute("authors", authors));
    }

    @Test
    void whenGetNewAuthorForm_thenReturnAuthorForm() throws Exception {
        mockMvc.perform(get("/authors/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("author/form"))
               .andExpect(model().attributeExists("author"));
    }

    @Test
    void whenCreateAuthor_thenRedirectToAuthorsList() throws Exception {
        Author author = new Author(null, "New Author", "new@example.com");
        when(authorService.saveAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/authors")
               .param("name", "New Author")
               .param("email", "new@example.com"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1)).saveAuthor(any(Author.class));
    }

    @Test
    void whenGetEditForm_thenReturnAuthorForm() throws Exception {
        Author author = new Author(1L, "John Doe", "john@example.com");
        when(authorService.getAuthorById(1L)).thenReturn(author);

        mockMvc.perform(get("/authors/edit/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("author/form"))
               .andExpect(model().attributeExists("author"))
               .andExpect(model().attribute("author", author));
    }

    @Test
    void whenUpdateAuthor_thenRedirectToAuthorsList() throws Exception {
        Author author = new Author(1L, "Updated Author", "updated@example.com");
        when(authorService.updateAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/authors/update")
               .param("id", "1")
               .param("name", "Updated Author")
               .param("email", "updated@example.com"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1)).updateAuthor(any(Author.class));
    }
}
