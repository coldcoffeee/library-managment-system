package com.librarymanagement.main.controller;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@PathVariable Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        System.out.println(book);
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteBook(bookId);
    }

    @GetMapping("/by-author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookService.getBooksByBookAuthor(author);
    }

    @GetMapping("/by-genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookService.getBooksByBookGenre(genre);
    }

    @GetMapping("/by-title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByBookTitle(title);
    }

    @GetMapping("/stock/{bookId}")
    public Integer getBookStock(@PathVariable Integer bookId) {
        return bookService.getBookStockByBookId(bookId);
    }
}
