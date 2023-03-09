package com.example.demo_security.controller;

import com.example.demo_security.model.Book;
import com.example.demo_security.repository.BookDAO;
import com.example.demo_security.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@Slf4j
public class BookController {

    private final BookDAO bookDAO;

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBook() {
        log.warn("[CONTROLLER] - GET ALL BOOK");
        return ResponseEntity.ok(bookDAO.getAll());
    }

    @GetMapping("/add")
    public void add() {
        log.warn("[CONTROLLER] - ADD");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        log.warn("[CONTROLLER] - GET BOOK BY ID: " + id);
        return ResponseEntity.ok(bookDAO.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Book> insertBook(Book book) {
        log.warn("[CONTROLLER] - SAVE NEW BOOK: " + book);
        return ResponseEntity.ok(bookDAO.save(book));
    }

    @GetMapping("/delete/{id}")
    public void deleteBook(@PathVariable Long id) {
        log.warn("[CONTROLLER] - DELETE BOOK BY ID: " + id);
        bookDAO.delete(id);
    }

    @GetMapping("/edit/{id}")
    public void updateBook(@PathVariable Long id, Book newBook) {
        log.warn("[CONTROLLER] - UPDATE BOOK BY ID: " + id);
        bookDAO.updateBook(id, newBook);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchGet(@RequestParam String key) {
        log.warn("[CONTROLLER] - SEARCH GET");
        return ResponseEntity.ok(bookDAO.search(key));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchPost(@RequestParam String key) {
        log.warn("[CONTROLLER] - SEARCH POST");
        return ResponseEntity.ok(bookDAO.search(key));
    }
}
