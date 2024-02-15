package com.librarymanagement.main.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.librarymanagement.main.entity.Book;
import com.librarymanagement.main.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {
        List<Book> expectedBooks = Arrays.asList(new Book(1, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 599.00, 100), new Book(2, "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 899.00, 75));
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertIterableEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetBookById() {
        Integer bookId = 1;
        Book expectedBook = new Book(1, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 599.00, 100);
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(expectedBook));

        Book actualBook = bookService.getBookById(bookId);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void testSaveBook() {
        Book book = new Book(3, "The Godfather", "Mario Puzo", "Crime Fiction", 799.00, 60);
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.saveBook(book);

        assertEquals(book, savedBook);
    }

    @Test
    public void testDeleteBook() {
        Integer bookId = 1;

        bookService.deleteBook(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testGetBooksByBookAuthor() {
        String author = "J.R.R. Tolkien";
        List<Book> expectedBooks = Arrays.asList(new Book(1, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 599.00, 100), new Book(2, "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 899.00, 75));
        when(bookRepository.findByBookAuthor(author)).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByBookAuthor(author);

        assertIterableEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetBooksByBookGenre() {
        String genre = "Children's Fiction";
        List<Book> expectedBooks = Arrays.asList(new Book(7, "Diary of a Wimpy Kid", "Jeff Kinney", "Children's Fiction", 399.00, 120), new Book(9, "Charlie and the Chocolate Factory", "Roald Dahl", "Children's Fiction", 449.00, 100));
        when(bookRepository.findByBookGenre(genre)).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByBookGenre(genre);

        assertIterableEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetBooksByBookTitle() {
        String title = "The Catcher in the Rye";
        List<Book> expectedBooks = Arrays.asList(new Book(1, "The Catcher in the Rye", "J.D. Salinger", "Fiction", 499.00, 50), new Book(2, "The Catcher in the Rye", "Haruki Murakami", "Fiction", 599.00, 40));
        when(bookRepository.findByBookTitle(title)).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByBookTitle(title);

        assertIterableEquals(expectedBooks, actualBooks);
    }

    @Test
    public void testGetBookStockByBookId() {
        Integer bookId = 1;
        Integer expectedStock = 100;
        when(bookRepository.findBookStockByBookId(bookId)).thenReturn(expectedStock);

        Integer actualStock = bookService.getBookStockByBookId(bookId);

        assertEquals(expectedStock, actualStock);
    }
}
