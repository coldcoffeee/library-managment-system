package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import com.librarymanagement.main.exception.*;
import com.librarymanagement.main.repository.BookRepository;
import com.librarymanagement.main.repository.LibraryUserRepository;
import com.librarymanagement.main.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LibraryUserRepository libraryUserRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Integer transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with ID " + transactionId + " not found"));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        Optional<LibraryUser> user = libraryUserRepository.findById(transaction.getUserId());
        Optional<Book> book = bookRepository.findById(transaction.getBookId());

        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + transaction.getBookId() + " not found");
        }
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with ID " + transaction.getUserId() + " not found");
        }

        var existingTransaction = transactionRepository.findIncompleteTransactionOfUser(user.get().getUserId());

        if(transactionRepository.findIncompleteTransactionOfUser(user.get().getUserId()) != null) {
            throw new OperationNotAllowedException("User must return the following book before borrowing a new one: " + bookRepository.getReferenceById(existingTransaction.getBookId()));
        }

        if (book.get().getBookStock() <= 0) {
            throw new InsufficientStockException("Sorry, the requested book is not in stock at the moment.");
        }

        book.get().setBookStock(book.get().getBookStock() - 1);
        bookRepository.save(book.get());
        transaction.initializeValues();
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(Integer userId) {
        LibraryUser user = libraryUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        return transactionRepository.findTransactionsByUserId(userId);
    }

    @Override
    public Double getLateFineByUserId(Integer userId) {
        LibraryUser user = libraryUserRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        updateUsersLateFine(userId);
        return transactionRepository.findIncompleteTransactionOfUser(userId).getLateFine();
    }

    @Override
    public List<Transaction> getBookTransactions(Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found"));
        return transactionRepository.findTransactionsByBookId(bookId);
    }

    @Override
    public List<LibraryUser> getUsersWithLateFineGreaterThan(Double amount) {
        updateLateFineForIncompleteTransactions();
        return transactionRepository.findByLateFineGreaterThan(amount);
    }

    @Override
    public void updateLateFineForIncompleteTransactions() {
        List<Transaction> incompleteTransactions = transactionRepository.findIncompleteTransactions();
        LocalDate now = LocalDate.now();
        for (Transaction t : incompleteTransactions) {
            if (t.getReturnDate().isAfter(now)) continue;
            t.setLateFine(ChronoUnit.DAYS.between(now, t.getReturnDate()) * 100.0);
            transactionRepository.save(t);
        }
    }

    @Override
    public void updateUsersLateFine(Integer userId) {
        Transaction t = transactionRepository.findIncompleteTransactionOfUser(userId);
        LocalDate now = LocalDate.now();
        if (t.getReturnDate().isAfter(now)) return;
        t.setLateFine(ChronoUnit.DAYS.between(now, t.getReturnDate()) * 100.0);
        transactionRepository.save(t);
    }

    @Override
    public List<Transaction> getTransactionsByIssueDate(LocalDate localDate) {
        return transactionRepository.findByIssueDate(localDate);
    }

    @Override
    public List<Transaction> getTransactionsByReturnDate(LocalDate localDate) {
        return transactionRepository.findByReturnDate(localDate);
    }

    @Override
    public List<Transaction> getTransactionsByIssueDateGreaterThan(LocalDate localDate) {
        return transactionRepository.findByIssueDateGreaterThan(localDate);
    }

    @Override
    public List<Transaction> getTransactionsByReturnDateGreaterThan(LocalDate localDate) {
        return transactionRepository.findByReturnDateGreaterThan(localDate);
    }

    @Override
    public Transaction renewTransaction(Integer transactionId) {
        Optional<Transaction> t = transactionRepository.findById(transactionId);
        if (t.isEmpty()) {
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found");
        }
        if (t.get().getCompletionStatus()) {
            throw new TransactionExpiredException("The book has already been returned, please re-issue.");
        }
        updateUsersLateFine(t.get().getUserId());
        LocalDate returnDate = LocalDate.now().plusDays(14);
        t.get().setReturnDate(returnDate);
        return transactionRepository.save(t.get());
    }

    @Override
    public Transaction receiveBook(Integer transactionId) {
        var t = transactionRepository.findById(transactionId);
        if (t.isEmpty()) {
            throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found");
        }
        if (t.get().getCompletionStatus()) {
            throw new TransactionExpiredException("The book has already been returned.");
        }
        updateUsersLateFine(t.get().getUserId());
        t.get().setCompletionStatus(true);
        t.get().setReturnDate(LocalDate.now());
        return transactionRepository.save(t.get());
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        if(transactionRepository.findById(transactionId).isEmpty()) throw new TransactionNotFoundException("Transaction with ID " + transactionId + " not found");
        transactionRepository.deleteById(transactionId);
    }
}
