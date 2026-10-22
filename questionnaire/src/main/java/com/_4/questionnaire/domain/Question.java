package com._4.questionnaire.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Question {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int _id;
    private String _answer;
    private String _quest;

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }
    public String getAnswer() {
        return _answer;
    }
    public void setAnswer(String answer) {
        _answer = answer;
    }
    public String getQuest() {
        return _quest;
    }
    public void set_quest(String quest) {
        _quest = quest;
    }
   
}
