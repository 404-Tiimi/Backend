package com._4.questionnaire.web;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com._4.questionnaire.domain.Answer;
import com._4.questionnaire.domain.AnswerRepository;
import com._4.questionnaire.domain.Inquiry;
import com._4.questionnaire.domain.InquiryRepository;
import com._4.questionnaire.domain.Question;
import com._4.questionnaire.domain.QuestionRepository;
import com._4.questionnaire.domain.Session;
import com._4.questionnaire.domain.SessionRepository;
import com._4.questionnaire.web.dto.AnswerRequest;
import com._4.questionnaire.web.dto.SaveAnswersRequest;


@RestController
@RequestMapping("/rest")
@CrossOrigin
public class InquiryRestController {

    // Repositoryt hoitavat tietojen hakemisen ja tallentamisen tietokantaan.
    private final InquiryRepository iRepository;
    private final SessionRepository sRepository;

    // Spring antaa tarvittavat repositoryt konstruktorille automaattisesti.
    public InquiryRestController(
            InquiryRepository iRepository, SessionRepository sRepository) {
        this.iRepository = iRepository;
        this.sRepository = sRepository;
    }

    // Palauttaa kaikki tietokannassa olevat kyselyt.
    @GetMapping("/inquiries")
    public List<Inquiry> findAllInquiriesRest() {
        return (List<Inquiry>) iRepository.findAll();
    }

    // Hakee yhden kyselyn sen ID:n perusteella.
    @GetMapping("/inquiry/{id}")
    public Optional<Inquiry> getOneInquiryRest(@PathVariable(name = "id") Integer id) {
        return iRepository.findById(id);
    }

    // Luo uuden vastaussession ja tallentaa sen alkamisajan.
    @PostMapping("/sessions")
    public Session createSession() {
        return sRepository.save(new Session(LocalDateTime.now()));
    }

}
