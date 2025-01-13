package com.LibraryManagement.demo.dao;

import com.LibraryManagement.demo.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.jdbcTemplate.update("CREATE TABLE Books (BookID int primary key auto_increment, Title varchar(50), Author varchar(50))");
    }

    public ResponseEntity<String> addBook(Book book) {
        String sql = "INSERT INTO Books (Title, Author) VALUES (?,?)";
        int rowsAffected = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor());

        if (rowsAffected > 0) {
            return new ResponseEntity<>("Book Added Successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Add to Delete Book", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteBook(int id) {
        String sql = "DELETE FROM Books WHERE BookID = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);

        if (rowsAffected > 0) {
            return new ResponseEntity<>("Book Deleted Successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed To Delete", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<Book> getAllBooks() {
        String sql = "SELECT * FROM Books";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Book book = new Book();

            book.setId(rs.getInt("BookID"));
            book.setTitle(rs.getString("Title"));
            book.setAuthor(rs.getString("Author"));

            return book;
        });
    }

    public Book findBookByID(int id) {
        String sql = "SELECT * FROM Books WHERE BookID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Book book = new Book();

            book.setId(rs.getInt("BookID"));
            book.setTitle(rs.getString("Title"));
            book.setAuthor(rs.getString("Author"));

            return book;
        });
    }
}
