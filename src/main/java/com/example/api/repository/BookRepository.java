package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}