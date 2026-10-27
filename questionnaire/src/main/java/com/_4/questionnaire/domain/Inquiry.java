package com._4.questionnaire.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity 
public class Inquiry {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int _id;
    private String _title;
    private String _author;
    private LocalDate _createDate;
    private LocalDate _deadlineDate;

    @OneToMany
    List<Question> _questions = new ArrayList<Question>();

    public Inquiry(int id,String title, String author,
        LocalDate createDate, LocalDate deadlineDate, List<Question> questions) {
        _id = id;
        _title = title;
        _author = author;
        _createDate = createDate;
        _deadlineDate = deadlineDate;
        _questions = questions;
    }

    public Inquiry(){

    }

    public void setId(int id) {
        _id = id;
    }
    public void setTitle(String title) {
        _title = title;
    }
    public void setAuthor(String author) {
        _author = author;
    }
    public void setCreateDate(LocalDate createDate) {
        _createDate = createDate;
    }
    public void setDeadlineDate(LocalDate deadlineDate) {
        _deadlineDate = deadlineDate;
    }

    public void setQuestions(List<Question> questions) {
        _questions = questions;
    }

    public int getId() {
        return _id;
    }
    public String getTitle() {
        return _title;
    }
    public String getAuthor() {
        return _author;
    }
    public LocalDate getCreateDate() {
        return _createDate;
    }
    public LocalDate getDeadlineDate() {
        return _deadlineDate;
    }

    public List<Question> getQuestions() {
        return _questions;
    }
    
}
