package com.example.bookstore.config;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save 10 authors
        for (int i = 1; i <= 10; i++) {
            Author author = new Author("Author " + i, "author" + i + "@example.com");
            authorRepository.save(author);

            // Create and save a book for each author
            Book book = new Book("Book " + i, "ISBN-" + i, author);
            bookRepository.save(book);
        }
    }
}
