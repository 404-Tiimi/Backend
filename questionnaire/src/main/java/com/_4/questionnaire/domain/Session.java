package com._4.questionnaire.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Session {

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;

    @Column(name="startedAt")
    private LocalDateTime _startedAt;

    @Column(name="completedAt")
    private LocalDateTime _completedAt;

}
