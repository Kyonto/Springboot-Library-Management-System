package com.example.bookstore.controller;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.AuthorService;
import com.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class SearchController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Book> books = bookService.getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        List<Author> authors = authorService.getAllAuthors().stream()
                .filter(author -> author.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        return "search/results";
    }
}
