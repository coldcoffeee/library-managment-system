package com.librarymanagement.main.controller;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import com.librarymanagement.main.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//        Transaction transaction = new Transaction(_transaction.getBookId(), _transaction.getUserId());
//        System.out.println(transaction);
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

    @PostMapping("/renew/{transactionId}")
    public Transaction renewTransaction(@PathVariable Integer transactionId) {
        return transactionService.renewTransaction(transactionId);
    }

    @PostMapping("/receive-book/{transactionId}")
    public Transaction receiveBook(@PathVariable Integer transactionId) {
        return transactionService.receiveBook(transactionId);
    }

//    @GetMapping("/by-return-date/{date:.+}")
    @GetMapping("/by-issue-date/{date:\\d{4}-\\d{2}-\\d{2}}")
    public List<Transaction> getTransactionsByIssueDate(@PathVariable String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(date);
        return transactionService.getTransactionsByIssueDate(parsedDate);
    }

    @GetMapping("/by-return-date/{dateStr:\\d{4}-\\d{2}-\\d{2}}")
    public List<Transaction> getTransactionsByReturnDate(@PathVariable String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateStr);
        return transactionService.getTransactionsByReturnDate(date);
    }

    @GetMapping("/by-issue-date-greater-than/{dateStr:\\d{4}-\\d{2}-\\d{2}}")
    public List<Transaction> getTransactionsByIssueDateGreaterThan(@PathVariable String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateStr);
        return transactionService.getTransactionsByIssueDateGreaterThan(date);
    }

    @GetMapping("/by-return-date-greater-than/{dateStr:\\d{4}-\\d{2}-\\d{2}}")
    public List<Transaction> getTransactionsByReturnDateGreaterThan(@PathVariable String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateStr);
        return transactionService.getTransactionsByReturnDateGreaterThan(date);
    }

}
