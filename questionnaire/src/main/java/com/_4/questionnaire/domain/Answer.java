package com._4.questionnaire.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity 
public class Answer {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;

    @ManyToOne
    @JoinColumn(name = "Question_id")
    private Question _question;

    @ManyToOne 
    @JoinColumn(name = "Session_id")
    private Session _session;

    @Column(name="answer")
    private String  _answer;

    public Answer(int id, Question question, Session session, String answer) {
        _id = id;
        _question = question;
        _session = session;
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

    public void setSessio(Session session) {
        _session = session;
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

    public Session getSessio() {
        return _session;
    }

    public String getAnswer() {
        return _answer;
    }

}
