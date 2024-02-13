package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import com.librarymanagement.main.repository.BookRepository;
import com.librarymanagement.main.repository.LibraryUserRepository;
import com.librarymanagement.main.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
        return transactionRepository.findById(transactionId).orElse(null);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        LibraryUser user = libraryUserRepository.getReferenceById(transaction.getUserId());
        Book book = bookRepository.getReferenceById(transaction.getBookId());
        if(book == null || user == null || transactionRepository.findIncompleteTransactionOfUser(user.getUserId()) != null || book.getBookStock() > 0) return null;
        book.setBookStock(book.getBookStock() - 1);
        bookRepository.save(book);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getUserTransactions(Integer userId) {
        return transactionRepository.findTransactionsByUserId(userId);
    }

    @Override
    public Double getLateFineByUserId(Integer userId) {
        updateUsersLateFine(userId);
        return transactionRepository.findIncompleteTransactionOfUser(userId).getLateFine();
    }

    @Override
    public List<Transaction> getBookTransactions(Integer bookId) {
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
        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        for(Transaction t: incompleteTransactions) {
            if(t.getReturnDate().after(new Date())) continue;
            LocalDate local = t.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            t.setLateFine(ChronoUnit.DAYS.between(now, local) * 100.0);
            transactionRepository.save(t);
        }
    }
    @Override
    public void updateUsersLateFine(Integer userId) {
        Transaction t = transactionRepository.findIncompleteTransactionOfUser(userId);
        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(t.getReturnDate().after(new Date())) return;
        LocalDate local = t.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        t.setLateFine(ChronoUnit.DAYS.between(now, local) * 100.0);
        transactionRepository.save(t);
    }

    @Override
    public List<Transaction> getTransactionsByIssueDate(Date date) {
        return transactionRepository.findByIssueDate(date);
    }

    @Override
    public List<Transaction> getTransactionsByReturnDate(Date date) {
        return transactionRepository.findByReturnDate(date);
    }

    @Override
    public List<Transaction> getTransactionsByIssueDateGreaterThan(Date date) {
        return transactionRepository.findByIssueDateGreaterThan(date);
    }

    @Override
    public List<Transaction> getTransactionsByReturnDateGreaterThan(Date date) {
        return transactionRepository.findByReturnDateGreaterThan(date);
    }

    @Override
    public Transaction renewTransaction(Integer transactionId) {
        Transaction t = transactionRepository.getReferenceById(transactionId);
        if(t == null) return null;
        updateUsersLateFine(t.getUserId());
        LocalDate returnDate = LocalDate.now().plusDays(14);
        t.setReturnDate(Date.from(returnDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return transactionRepository.save(t);
    }

    @Override
    public Transaction receiveBook(Integer transactionId) {
        Transaction t = transactionRepository.getReferenceById(transactionId);
        updateUsersLateFine(t.getUserId());
        t.setCompletionStatus(false);
        t.setReturnDate(new Date());
        return transactionRepository.save(t);
    }

    @Override
    public void deleteTransaction(Integer transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
