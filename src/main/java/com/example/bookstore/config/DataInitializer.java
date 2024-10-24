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
        for (int i = 1; i <= 10; i++) {
            Author author = new Author("Author " + i, "author" + i + "@example.com");
            authorRepository.save(author);

            Book book = new Book("Book " + i, "ISBN-" + i, author, 5); // Set initial available copies
            bookRepository.save(book);
        }
    }
}
