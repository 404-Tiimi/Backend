package com._4.questionnaire.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;

@Entity
public class Question {
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_RADIO = "RADIO";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int _id;


    @Column(name="question")
    private String _quest;

    @Column(name="type")
    private String _type;

    // Monivalintakysymyksen vaihtoehdot (vain RADIO-tyypillä)
    @ElementCollection
    @CollectionTable(name = "question_option", joinColumns = @JoinColumn(name = "question_id"))
    @OrderColumn(name = "position")
    @Column(name = "text")
    private List<String> _options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="inquiry_id")
    @JsonIgnore
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

    @JsonIgnore
    public Inquiry getInquiry() {
        return _inquiry;
    }
    public void setInquiry(Inquiry _inquiry) {
        this._inquiry = _inquiry;
    }

    // Vanhat kysymykset (type = null tai esim. "Text") tulkitaan avoimiksi
    public String getType() {
        return TYPE_RADIO.equalsIgnoreCase(_type) ? TYPE_RADIO : TYPE_TEXT;
    }
    public void setType(String type) {
        this._type = type;
    }

    public List<String> getOptions() {
        return _options;
    }
    public void setOptions(List<String> options) {
        this._options = options;
    }

    // Thymeleafin textareaa varten: yksi vaihtoehto per rivi
    @JsonIgnore
    public String getOptionsText() {
        return String.join("\n", _options);
    }
}
