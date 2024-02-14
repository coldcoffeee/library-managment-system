package com.librarymanagement.main.repository;

import com.librarymanagement.main.entity.LibraryUser;
import com.librarymanagement.main.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction>  findTransactionsByUserId(Integer userId);
    List<Transaction>  findTransactionsByBookId(Integer bookId);
    List<LibraryUser> findByLateFineGreaterThan(Double amount);

    @Query("SELECT t from Transaction t where t.completionStatus = false")
    List<Transaction>  findIncompleteTransactions();

    @Query("SELECT t from Transaction t where t.completionStatus = false AND t.userId = :userId")
    Transaction  findIncompleteTransactionOfUser(Integer userId);

    List<Transaction> findByIssueDate(LocalDate date);

    List<Transaction> findByReturnDate(LocalDate date);

    List<Transaction> findByIssueDateGreaterThan(LocalDate date);

    List<Transaction> findByReturnDateGreaterThan(LocalDate date);
}
