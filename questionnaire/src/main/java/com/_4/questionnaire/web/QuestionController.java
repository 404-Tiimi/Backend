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
    private InquiryRepository iRepository;
    private QuestionRepository qRepository;
    private UserRepository uRepository;

    public QuestionController (AnswerRepository aRepository,
    InquiryRepository iRepository, QuestionRepository qRepository,
    UserRepository uRepository){
        this.aRepository = aRepository;
        this.iRepository = iRepository;
        this.qRepository = qRepository;
        this.uRepository = uRepository;
    }

    // Page to create inquiry
    @RequestMapping(value = "/")
    public String index(Model model){
        model.addAttribute("inquiries", iRepository.findAll());
        return "index.html"; // index
    }

    // Page to customize inquiry
    @RequestMapping(value = "/inquiry")
    public String inquiry(){
        return "inquiry.html"; // inquiry.html
    }

    // Method to save created inquiry
    @RequestMapping(value = "/save",method=RequestMethod.POST)
    public String save(@RequestParam("title") String title,
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

    @RequestMapping(value = "/delete/{_id}", method = RequestMethod.GET)
    public String deleteInquiry(@PathVariable("_id") Long inquiryId, Model model){
        return "redirect:/"; // redirect to default page
    }

    @RequestMapping(value="/edit/{_id}")
    public String editInquiry(@PathVariable("_id") Long inquiryId, Model model){
        return ""; // editInquiry.html
    }


}
