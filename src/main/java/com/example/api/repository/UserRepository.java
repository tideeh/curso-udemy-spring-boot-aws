package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // Query é feita no objeto, e nao no banco
    @Query("SELECT u FROM User u WHERE u.userName=:userName")
    User findByUsername(@Param("userName") String userName);
}