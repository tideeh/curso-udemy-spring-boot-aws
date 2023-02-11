package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // Query é feita no objeto, e nao no banco
    @Modifying // como a alteração no banco foi criada por nós, precisamos garantir o ACID com essa annotation
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id=:id")
    void disablePerson(@Param("id") Long id);

    // Query é feita no objeto, e nao no banco
    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
    Page<Person> findPersonsByName(@Param("firstName") String firstName, Pageable pageable);
}