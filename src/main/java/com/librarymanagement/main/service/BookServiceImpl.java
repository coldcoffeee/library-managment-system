package com.librarymanagement.main.service;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.exception.BookNotFoundException;
import com.librarymanagement.main.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Book> book =  bookRepository.findById(bookId);
        if(book.isEmpty()) throw new BookNotFoundException("No such book with the given ID exists.");
        else return book.get();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Integer bookId) {
        if(bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException("No such book with the given ID exists.");
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getBooksByBookAuthor(String author) {
        List<Book> books = bookRepository.findByBookAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the author: " + author);
        }
        return books;
    }

    @Override
    public List<Book> getBooksByBookGenre(String genre) {
        List<Book> books = bookRepository.findByBookGenre(genre);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the genre: " + genre);
        }
        return books;
    }

    @Override
    public List<Book> getBooksByBookTitle(String title) {
        List<Book> books = bookRepository.findByBookTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the title: " + title);
        }
        return books;
    }

    @Override
    public Integer getBookStockByBookId(Integer bookId) {
        Integer stock = bookRepository.findBookStockByBookId(bookId);
        if (stock == null) {
            throw new BookNotFoundException("No stock information found for book with ID: " + bookId);
        }
        return stock;
    }
}
