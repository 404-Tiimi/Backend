package com._4.questionnaire.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InquiryRepository extends CrudRepository <Inquiry, Integer> {
    @Query("SELECT i FROM Inquiry i WHERE i._title = :title")
    Optional<Inquiry> findByTitle(@Param("title") String title);
}
