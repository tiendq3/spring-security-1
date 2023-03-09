package com.example.demo_security.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DAO<T> {

    List<T> getAll();

    Object getById(Long id);

    T save(T object);

    void delete(Long id);


    List<?> search(String key);
}
