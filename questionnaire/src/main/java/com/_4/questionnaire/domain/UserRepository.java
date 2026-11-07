package com._4.questionnaire.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository <User, Integer>{
    @Query("SELECT u FROM User u WHERE u._username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
