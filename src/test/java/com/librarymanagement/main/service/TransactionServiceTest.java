package com.librarymanagement.main.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import com.librarymanagement.main.exception.*;
import com.librarymanagement.main.repository.BookRepository;
import com.librarymanagement.main.repository.LibraryUserRepository;
import com.librarymanagement.main.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testGetAllTransactions() {
        List<Transaction> expectedTransactions = Arrays.asList(
                new  Transaction(
                1,
                123,
                456,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                0.0,
                false
        ),
                 new Transaction(
                2,
                123,
                456,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                0.0,
                false
        ));
        when(transactionRepository.findAll()).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionService.getAllTransactions();

        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetTransactionById() {
        Integer transactionId = 1;

        Transaction expectedTransaction = new Transaction(
                1,
                123,
                456,
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                0.0,
                false
        );


        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(expectedTransaction));

        Transaction actualTransaction = transactionService.getTransactionById(transactionId);

        assertEquals(expectedTransaction, actualTransaction);
    }


    @Test
    public void testGetUserTransactions() {
        Integer userId = 1;
        LibraryUser user = new LibraryUser(userId, "Rohit", "rohit@gmail.com",9293847565L);
        List<Transaction> expectedTransactions = Arrays.asList(
                new  Transaction(
                        1,
                        1,
                        1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(14),
                        0.0,
                        false
                ),
                new Transaction(
                        2,
                        2,
                        1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(14),
                        0.0,
                        false
                ));
        when(libraryUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findTransactionsByUserId(userId)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionService.getUserTransactions(userId);

        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void testGetLateFineByUserId() {
        Integer userId = 1;
        Double expectedLateFine = -200.0;
        LocalDate returnDate = LocalDate.now().minusDays(2);
        Transaction incompleteTransaction = new Transaction();
        incompleteTransaction.setTransactionId(1);
        incompleteTransaction.setBookId(123);
        incompleteTransaction.setUserId(456);
        incompleteTransaction.setIssueDate(LocalDate.now());
        incompleteTransaction.setCompletionStatus(false);
        incompleteTransaction.setLateFine(expectedLateFine);
        incompleteTransaction.setReturnDate(returnDate);

        LibraryUser user = new LibraryUser(1, "Rohit", "rohit@gmail.com", 9293847565L);
        user.setUserId(userId);

        when(libraryUserRepository.findById(userId)).thenReturn(Optional.of(user));
        when(transactionRepository.findIncompleteTransactionOfUser(userId)).thenReturn(incompleteTransaction);

        Double actualLateFine = transactionService.getLateFineByUserId(userId);

        assertEquals(expectedLateFine, actualLateFine);
    }
}