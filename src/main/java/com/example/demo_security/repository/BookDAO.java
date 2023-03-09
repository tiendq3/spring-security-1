package com.example.demo_security.repository;

import com.example.demo_security.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO implements DAO<Book> {

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Book> search(String key) {
        return null;
    }

    @Override
    public Book save(Book object) {
        return null;
    }

    public Book updateBook(Long id, Book newBook) {
        Book book = getById(id);
        book.setTitle(newBook.getTitle());
        book.setDescription(newBook.getDescription());
        return save(book);
    }
}
