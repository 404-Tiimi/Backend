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

    // Lisää uuden kyselyn ja sen kysymykset tietokantaan
    @RequestMapping(value = "/add",method=RequestMethod.POST)
    public String add(@RequestParam("title") String title,
            @RequestParam("questions") List<String> questionTexts){
        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(title);
        inquiry.setCreateDate(LocalDate.now());

        for (String questionText : questionTexts) {
            if (questionText != null && !questionText.isBlank()) {
                Question question = new Question();
                question.setQuestion(questionText.trim());
                inquiry.addQuestion(question);
            }
        }

        iRepository.save(inquiry);
        return "redirect:/"; // redirect to default page
    }

    // Tallentaa muokkaukset olemassa olevaan kyselyyn
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("id") Integer inquiryId,
            @RequestParam("title") String title,
            @RequestParam("questions") List<String> questionTexts) {
        Inquiry inquiry = iRepository.findById(inquiryId).orElseThrow();
        inquiry.setTitle(title);

        List<Question> existingQuestions = inquiry.getQuestions();
        int questionIndex = 0;
        for (String questionText : questionTexts) {
            if (questionText != null && !questionText.isBlank()) {
                String updatedText = questionText.trim();
                if (questionIndex < existingQuestions.size()) {
                    Question question = existingQuestions.get(questionIndex);
                    if (!updatedText.equals(question.getQuest())) {
                        question.setQuestion(updatedText);
                    }
                } else {
                    Question question = new Question();
                    question.setQuestion(updatedText);
                    inquiry.addQuestion(question);
                }
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


}
