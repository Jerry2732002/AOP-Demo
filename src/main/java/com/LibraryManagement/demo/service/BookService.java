package com.LibraryManagement.demo.service;

import com.LibraryManagement.demo.annotation.Audit;
import com.LibraryManagement.demo.dao.BookRepository;
import com.LibraryManagement.demo.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Audit
    public ResponseEntity<String> addBook(Book book) {
        return bookRepository.addBook(book);
    }

    @Audit
    public ResponseEntity<String> deleteBook(int id) {
        return bookRepository.deleteBook(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book findBookById(int id) {
        Book book = bookRepository.findBookByID(id);

        if (book == null) {
            throw new RuntimeException("Book Not Found");
        }

        return book;
    }
}
