package com.librarymanagement.main.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookId;
    @Column
    @NotEmpty(message = "Please provide a book title.")
    private String bookTitle;
    @Column
    @NotEmpty(message = "Please provide the author's name.")
    private String bookAuthor;
    @Column
    @NotEmpty(message = "Please provide the genre.")
    private String bookGenre;
    @Column
    @NotNull(message = "Please provide the book's price.")
    private Double bookPrice;
    @Column
    @NotNull(message = "Please specify the book's stock.")
    @Min(value = 1, message = "Book stock must at least be 1.")
    private Integer bookStock;

    public Book() {
    }

    public Book(Integer bookId, String bookTitle, String bookAuthor, String bookGenre, Double bookPrice, Integer bookStock) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.bookPrice = bookPrice;
        this.bookStock = bookStock;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookStock() {
        return bookStock;
    }

    public void setBookStock(Integer bookStock) {
        this.bookStock = bookStock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookGenre='" + bookGenre + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookStock=" + bookStock +
                '}';
    }
}
