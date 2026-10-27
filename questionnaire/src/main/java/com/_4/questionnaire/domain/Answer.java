package com._4.questionnaire.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity 
public class Answer {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int _id;

    @OneToMany 
    @JoinColumn (name ="question_id")
    private Question _question;

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int _sessio_id;

    private String  _answer;

    public Answer(int id, Question question, int sessio_id, String answer) {
        _id = id;
        _question = question;
        _sessio_id = sessio_id;
        _answer = answer;
    }

    public Answer(){

    }

    public void setId(int id) {
        _id = id;
    }

    public void setQuestion(Question question) {
        _question = question;
    }

    public void setSessio_id(int sessio_id) {
        _sessio_id = sessio_id;
    }

    public void setAnswer(String answer) {
        _answer = answer;
    }

    public int getId() {
        return _id;
    }

    public Question getQuestion() {
        return _question;
    }

    public int getSessio_id() {
        return _sessio_id;
    }

    public String getAnswer() {
        return _answer;
    }

}

