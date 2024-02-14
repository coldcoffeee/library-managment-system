package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Integer bookId);

    Book saveBook(Book book);

    void deleteBook(Integer bookId);

    List<Book> getBooksByBookAuthor(String author);

    List<Book> getBooksByBookGenre(String genre);

    List<Book> getBooksByBookTitle(String title);

    Double getBookStockByBookId(Integer bookId);
}
