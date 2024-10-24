package com.example.bookstore.controller;

import com.example.bookstore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public String listTransactions(Model model) {
        model.addAttribute("transactions", transactionService.getAllTransactions());
        return "transaction/list";
    }

    @PostMapping("/books/{id}/borrow")
    public String borrowBook(@PathVariable Long id) {
        transactionService.borrowBook(id);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/return")
    public String returnBook(@PathVariable Long id) {
        transactionService.returnBook(id);
        return "redirect:/books";
    }
}
