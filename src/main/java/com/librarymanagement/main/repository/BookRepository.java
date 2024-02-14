package com.librarymanagement.main.repository;

import com.librarymanagement.main.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookAuthor(String author);

    List<Book> findByBookGenre(String genre);

    List<Book> findByBookTitle(String title);

    Double findBookStockByBookId(Integer bookId);
}
