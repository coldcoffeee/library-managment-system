package com.librarymanagement.main.entity;

import jakarta.persistence.*;

import java.util.Calendar;
import java.util.Date;

@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;
    @Column
    private Integer bookId;
    @Column
    private Integer userId;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    @Column
    private Date returnDate;
    @Column
    private Double lateFine;
    @Column
    private Boolean completionStatus;

    private static Date calculateReturnDate(Date issueDate) {
        long returnDateInMillis = issueDate.getTime() + (14 * 24 * 60 * 60 * 1000);
        return new Date(returnDateInMillis);
    }

    public Transaction() {
    }

    public Transaction(Integer transactionId, Integer bookId, Integer userId, Double lateFine, Boolean completionStatus) {
        this.transactionId = transactionId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = new Date();
        this.returnDate = calculateReturnDate(this.issueDate);
        this.lateFine = 0.0;
        this.completionStatus = false;
    }

    public void initializeValues() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        this.issueDate = calendar.getTime();
        this.returnDate = calculateReturnDate(this.issueDate);
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
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

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
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
