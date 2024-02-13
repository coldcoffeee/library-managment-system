package com.librarymanagement.main.controller;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import com.librarymanagement.main.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransactionById(@PathVariable Integer transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable Integer transactionId) {
        transactionService.deleteTransaction(transactionId);
    }

    @GetMapping("/by-user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable Integer userId) {
        return transactionService.getUserTransactions(userId);
    }

    @GetMapping("/by-book/{bookId}")
    public List<Transaction> getBookTransactions(@PathVariable Integer bookId) {
        return transactionService.getBookTransactions(bookId);
    }

    @GetMapping("/by-user-fine/{amount}")
    public List<LibraryUser> getUsersWithFineGreaterThan(@PathVariable Double amount) {
        return transactionService.getUsersWithLateFineGreaterThan(amount);
    }
}
