package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.service.BookService;
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
class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void whenGetBooks_thenReturnBooksList() throws Exception {
        Author author = new Author("John Doe", "john@example.com");
        List<Book> books = Arrays.asList(
            new Book("Book 1", "1234567890", author, 5),
            new Book("Book 2", "0987654321", author, 5)
        );
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
               .andExpect(status().isOk())
               .andExpect(view().name("book/list"))
               .andExpect(model().attributeExists("books"))
               .andExpect(model().attribute("books", books));
    }

    @Test
    void whenGetNewBookForm_thenReturnBookForm() throws Exception {
        List<Author> authors = Arrays.asList(
            new Author("John Doe", "john@example.com"),
            new Author("Jane Doe", "jane@example.com")
        );
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/books/new"))
               .andExpect(status().isOk())
               .andExpect(view().name("book/form"))
               .andExpect(model().attributeExists("book"))
               .andExpect(model().attributeExists("authors"))
               .andExpect(model().attribute("authors", authors));
    }

    @Test
    void whenCreateBook_thenRedirectToBooksList() throws Exception {
        Author author = new Author("John Doe", "john@example.com");
        Book book = new Book("New Book", "1234567890", author, 5);
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
               .param("title", "New Book")
               .param("isbn", "1234567890")
               .param("author.id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).saveBook(any(Book.class));
    }

    @Test
    void whenGetEditForm_thenReturnBookForm() throws Exception {
        Author author = new Author("John Doe", "john@example.com");
        Book book = new Book("Book 1", "1234567890", author, 5);
        List<Author> authors = Arrays.asList(author);

        when(bookService.getBookById(1L)).thenReturn(book);
        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(get("/books/edit/1"))
               .andExpect(status().isOk())
               .andExpect(view().name("book/form"))
               .andExpect(model().attributeExists("book"))
               .andExpect(model().attributeExists("authors"))
               .andExpect(model().attribute("book", book))
               .andExpect(model().attribute("authors", authors));
    }

    @Test
    void whenUpdateBook_thenRedirectToBooksList() throws Exception {
        Author author = new Author("John Doe", "john@example.com");
        Book book = new Book("Updated Book", "0987654321", author, 5);
        when(bookService.updateBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books/update")
               .param("id", "1")
               .param("title", "Updated Book")
               .param("isbn", "0987654321")
               .param("author.id", "1"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).updateBook(any(Book.class));
    }
}
