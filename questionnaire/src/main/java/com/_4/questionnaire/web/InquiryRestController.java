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
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SessionRepository sessionRepository;

    // Spring antaa tarvittavat repositoryt konstruktorille automaattisesti.
    public InquiryRestController(
            InquiryRepository iRepository,
            AnswerRepository answerRepository,
            QuestionRepository questionRepository,
            SessionRepository sessionRepository) {
        this.iRepository = iRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.sessionRepository = sessionRepository;
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
        return sessionRepository.save(new Session(LocalDateTime.now()));
    }

    // Tallentaa kaikki pyynnössä lähetetyt vastaukset samaan sessioon.
    @PostMapping("/answers")
    public Iterable<Answer> saveAnswers(@RequestBody SaveAnswersRequest request) {
        // Haetaan olemassa oleva sessio pyynnössä annetulla ID:llä.
        Session session = sessionRepository.findById(request.sessionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Session not found"));

        // Muutetaan jokainen DTO-tietue Answer-entiteetiksi.
        List<Answer> answers = request.answers().stream()
                .map(answerRequest -> createAnswer(answerRequest, session))
                .toList();

        // Tallennetaan kaikki vastaukset tietokantaan.
        return answerRepository.saveAll(answers);
    }

    // Luo Answer-entiteetin ja yhdistää sen kysymykseen ja sessioon.
    private Answer createAnswer(AnswerRequest request, Session session) {
        // Haetaan vastausta vastaava kysymys sen ID:n perusteella.
        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found"));

        // Asetetaan vastauksen kysymys, sessio ja tekstisisältö.
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setSession(session);
        answer.setAnswer(request.text());
        return answer;
    }

}
