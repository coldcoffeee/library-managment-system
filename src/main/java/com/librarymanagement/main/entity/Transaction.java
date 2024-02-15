package com.librarymanagement.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.*;

@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    @Column
    @NotEmpty(message = "Please provide the id of the book to lend.")
    private Integer bookId;
    @Column
    @NotEmpty(message = "Please provide the id of the user lending the book.")
    private Integer userId;
    @Column
    @NotEmpty(message = "Please provide the issue date.")
    private LocalDate issueDate;
    @Column
    @NotEmpty
    private LocalDate returnDate;
    @Column
    @NotEmpty
    private Double lateFine;
    @Column
    @NotEmpty
    private Boolean completionStatus;


    public Transaction() {
    }

    public Transaction(Integer transactionId, Integer bookId, Integer userId, Double lateFine, Boolean completionStatus) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = LocalDate.now();
        this.returnDate = this.issueDate.plusDays(14);
        this.lateFine = 0.0;
        this.completionStatus = false;
    }

    public void initializeValues() {
        this.issueDate = LocalDate.now();
        this.returnDate = this.issueDate.plusDays(14);
        this.lateFine = 0.0;
        this.completionStatus = false;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Double getLateFine() {
        return lateFine;
    }

    public void setLateFine(Double lateFine) {
        this.lateFine = lateFine;
    }

    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setCompletionStatus(Boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", issueDate=" + issueDate +
                ", lateFine=" + lateFine +
                ", completionStatus=" + completionStatus +
                '}';
    }
}
