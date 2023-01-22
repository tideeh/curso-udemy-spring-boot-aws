package com.example.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}