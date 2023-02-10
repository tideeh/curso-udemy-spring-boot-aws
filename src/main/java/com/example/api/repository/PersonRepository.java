package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // Query é feita no objeto, e nao no banco
    @Modifying // como a alteração no banco foi criada por nós, precisamos garantir o ACID com essa annotation
    @Query("UPDATE Person p SET p.enabled = false WHERE p.id=:id")
    void disablePerson(@Param("id") Long id);
}