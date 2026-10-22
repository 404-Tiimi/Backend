package com._4.questionnaire.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
public class QuestionController {

    // Page to create inquiry
    @RequestMapping(value = "/")
    public String index(){
        return "index.html"; // index
    }

    // Page to customize inquiry
    @RequestMapping(value = "/inquiry")
    public String inquiry(){
        return "inquiry.html"; // inquiry.html
    }

    // Method to save created inquiry
    @RequestMapping(value = "/save",method=RequestMethod.POST)
    public String save(){
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
