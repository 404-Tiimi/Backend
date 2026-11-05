package com._4.questionnaire.web;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com._4.questionnaire.domain.Inquiry;
import com._4.questionnaire.domain.InquiryRepository;

@RestController 
@RequestMapping ("/rest")
@CrossOrigin 
public class InquiryRestController {

    private InquiryRepository iRepository;

    public InquiryRestController(InquiryRepository iRepository){
        this.iRepository = iRepository;
    }

    @GetMapping ("/inquiries")
    public @ResponseBody List<Inquiry> findAllInquiriesRest(){
        return (List<Inquiry>) iRepository.findAll();
    }

    @GetMapping ("/inquiry/{id}")
    public @ResponseBody Optional<Inquiry> getOneInquiryRest(@PathVariable 
        (name= "id") Integer id){
            return iRepository.findById(id);
    }
}
