package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.TransactionType;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction borrowBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
            Transaction transaction = new Transaction(book, LocalDateTime.now(), TransactionType.BORROW);
            return transactionRepository.save(transaction);
        } else {
            throw new IllegalStateException("No copies available");
        }
    }

    public Transaction returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);
        Transaction transaction = new Transaction(book, LocalDateTime.now(), TransactionType.RETURN);
        return transactionRepository.save(transaction);
    }
}
