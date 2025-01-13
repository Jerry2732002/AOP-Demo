package com.LibraryManagement.demo.controller;

import com.LibraryManagement.demo.dto.Book;
import com.LibraryManagement.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "book", produces = "application/json")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "book/{id}", produces = "application/json")
    public Book findBookById(@PathVariable("id") int id){
        return bookService.findBookById(id);
    }

    @PostMapping(path = "book")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @DeleteMapping(path = "book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        return bookService.deleteBook(id);
    }
}
