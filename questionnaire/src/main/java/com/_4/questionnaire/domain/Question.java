package com._4.questionnaire.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;

    
    @Column(name="question")
    private String _quest;

    @Column(name="type")
    private String _type;

    @ManyToOne 
    @JoinColumn(name="inquiry_id")
    private Inquiry _inquiry;

    
    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }

    public String getQuest() {
        return _quest;
    }
    public void setQuestion(String quest) {
        _quest = quest;
    }

    public Inquiry getInquiry() {
        return _inquiry;
    }
    public void setInquiry(Inquiry _inquiry) {
        this._inquiry = _inquiry;
    }
    public String get_type() {
        return _type;
    }
    public void set_type(String _type) {
        this._type = _type;
    }

    
   
}
