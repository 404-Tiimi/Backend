package com._4.questionnaire.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com._4.questionnaire.domain.AnswerRepository;
import com._4.questionnaire.domain.Inquiry;
import com._4.questionnaire.domain.InquiryRepository;
import com._4.questionnaire.domain.Question;
import com._4.questionnaire.domain.QuestionRepository;
import com._4.questionnaire.domain.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller 
public class QuestionController {

    private AnswerRepository aRepository;
    private QuestionRepository qRepository;
    private UserRepository uRepository;
    private InquiryRepository iRepository;
    

    public QuestionController (AnswerRepository aRepository,
    InquiryRepository iRepository, QuestionRepository qRepository,
    UserRepository uRepository){
        this.aRepository = aRepository;
        this.iRepository = iRepository;
        this.qRepository = qRepository;
        this.uRepository = uRepository;
    }

    // Näyttää aloitussivun, jossa kaikki kyselyt on listattu
    @RequestMapping(value = "/")
    public String index(Model model){
        model.addAttribute("inquiries", iRepository.findAll());
        return "index.html"; // index
    }

    // Näyttää lomakkeen uuden kyselyn luomista varten
    @RequestMapping(value = "/inquiry")
    public String inquiry(){
        return "inquiry.html"; // inquiry.html
    }

    // Näyttää yhden kyselyn tiedot
    @RequestMapping (value = "/view/{id}")
    public String viewInquiry(@PathVariable ("id") Integer id ,Model model){
        Inquiry inquiry = iRepository.findById(id).orElse(null);
        model.addAttribute("inquiry", inquiry);
        return "inquirydetails"; // inquirydetail.html
    }

    // Lisää uuden kyselyn ja sen kysymykset tietokantaan.
    // Lomakkeen kentät luetaan getParameterValues-metodilla, jotta
    // @RequestParam ei pilko yksittäistä arvoa pilkuista.
    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public String add(@RequestParam("title") String title, HttpServletRequest request){
        return add(title,
                request.getParameterValues("questions"),
                request.getParameterValues("types"),
                request.getParameterValues("options"));
    }

    String add(String title, String[] questions, String[] types, String[] options) {
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(title);
        inquiry.setCreateDate(LocalDate.now());

        if (questions != null) {
            for (int i = 0; i < questions.length; i++) {
                String questionText = questions[i];
                if (questionText != null && !questionText.isBlank()) {
                    Question question = new Question();
                    question.setQuestion(questionText.trim());
                    applyTypeAndOptions(question, valueAt(types, i), valueAt(options, i));
                    inquiry.addQuestion(question);
                }
            }
        }

        iRepository.save(inquiry);
        return "redirect:/"; // redirect to default page
    }

    // Tallentaa muokkaukset olemassa olevaan kyselyyn
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") Integer inquiryId,
            @RequestParam("title") String title,
            HttpServletRequest request) {
        return save(inquiryId, title,
                request.getParameterValues("questions"),
                request.getParameterValues("types"),
                request.getParameterValues("options"));
    }

    String save(Integer inquiryId, String title,
            String[] questions, String[] types, String[] options) {
        Inquiry inquiry = iRepository.findById(inquiryId).orElseThrow();
        inquiry.setTitle(title);

        List<Question> existingQuestions = inquiry.getQuestions();
        int questionIndex = 0;
        if (questions != null) {
            for (int i = 0; i < questions.length; i++) {
                String questionText = questions[i];
                if (questionText == null || questionText.isBlank()) {
                    continue;
                }
                String updatedText = questionText.trim();
                Question question;
                if (questionIndex < existingQuestions.size()) {
                    question = existingQuestions.get(questionIndex);
                    if (!updatedText.equals(question.getQuest())) {
                        question.setQuestion(updatedText);
                    }
                } else {
                    question = new Question();
                    question.setQuestion(updatedText);
                    inquiry.addQuestion(question);
                }
                applyTypeAndOptions(question, valueAt(types, i), valueAt(options, i));
                questionIndex++;
            }
        }

        for (int i = existingQuestions.size() - 1; i >= questionIndex; i--) {
            inquiry.removeQuestion(existingQuestions.get(i));
        }

        iRepository.save(inquiry);
        return "redirect:/";
    }

    // Poistaa kyselyn
    @RequestMapping(value = "/delete/{_id}", method = RequestMethod.GET)
    public String deleteInquiry(@PathVariable("_id") Long inquiryId, Model model){
        return "redirect:/"; // redirect to default page
    }

    // Hakee muokattavan kyselyn lomakkeelle
    @RequestMapping(value="/edit/{_id}")
    public String editInquiry(@PathVariable("_id") Integer inquiryId, Model model){
        Inquiry inquiry = iRepository.findById(inquiryId).orElseThrow();
        model.addAttribute("inquiry", inquiry);
        return "inquiry";
    }

    // Asettaa kysymyksen tyypin ja (monivalinnalle) vaihtoehdot, yksi per rivi
    private void applyTypeAndOptions(Question q, String type, String optionsText) {
        q.setType(Question.TYPE_RADIO.equals(type) ? Question.TYPE_RADIO : Question.TYPE_TEXT);
        q.getOptions().clear();
        if (Question.TYPE_RADIO.equals(type) && optionsText != null) {
            for (String line : optionsText.split("\\R")) {
                if (!line.isBlank()) {
                    q.getOptions().add(line.trim());
                }
            }
        }
    }

    private static String valueAt(String[] values, int index) {
        return values != null && index < values.length ? values[index] : null;
    }

}
