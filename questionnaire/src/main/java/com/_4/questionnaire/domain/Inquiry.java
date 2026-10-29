package com._4.questionnaire.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity 
public class Inquiry {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;
    
    @Column(name="title")
    private String _title;

    @ManyToOne
    @JoinColumn(name="User_id")
    private User _author;

    @Column(name = "createDate")
    private LocalDate _createDate;

    @Column(name = "deadline")
    private LocalDate _deadlineDate;

    // referencing variable name in Questions.java, not the db column name
    @OneToMany(mappedBy = "_inquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Question> _questions = new ArrayList<>();

    public Inquiry(int id,String title, User author,
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
    public void setAuthor(User author) {
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

    public void addQuestion(Question question) {
        _questions.add(question);
        question.setInquiry(this);
    }

    public void removeQuestion(Question question) {
        _questions.remove(question);
        question.setInquiry(null);
    }

    public int getId() {
        return _id;
    }
    public String getTitle() {
        return _title;
    }
    public User getAuthor() {
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
