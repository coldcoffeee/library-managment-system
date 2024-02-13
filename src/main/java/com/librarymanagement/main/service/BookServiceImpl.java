package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Integer bookId) {
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getBooksByBookAuthor(String author) {
        return bookRepository.findByBookAuthor(author);
    }

    @Override
    public List<Book> getBooksByBookGenre(String genre) {
        return bookRepository.findByBookGenre(genre);
    }

    @Override
    public List<Book> getBooksByBookTitle(String title) {
        return bookRepository.findByBookTitle(title);
    }

    @Override
    public Integer getBookStockByBookId(Integer bookId) {
        return bookRepository.findBookStockByBookId(bookId);
    }
}
