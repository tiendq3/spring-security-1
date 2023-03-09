package com.example.demo_security.service;

import com.example.demo_security.repository.BookDAO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {
    private final BookDAO bookDAO;
}
