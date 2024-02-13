package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Integer transactionId);

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getUserTransactions(Integer userId);

    List<Transaction> getBookTransactions(Integer bookId);

    List<LibraryUser> getUsersWithLateFineGreaterThan(Double amount);

    Double getLateFineByUserId(Integer userId);

    void updateLateFineForIncompleteTransactions();

    void updateUsersLateFine(Integer userId);

    List<Transaction> getTransactionsByIssueDate(Date date);

    List<Transaction> getTransactionsByReturnDate(Date date);

    List<Transaction> getTransactionsByIssueDateGreaterThan(Date date);

    List<Transaction> getTransactionsByReturnDateGreaterThan(Date date);

    Transaction renewTransaction(Integer transactionId);

    Transaction receiveBook(Integer transactionId);

    void deleteTransaction(Integer transactionId);
}
