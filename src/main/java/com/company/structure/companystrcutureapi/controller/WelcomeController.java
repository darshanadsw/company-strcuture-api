package com.company.structure.companystrcutureapi.controller;

import com.company.structure.companystrcutureapi.domain.PhoneType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.Collection;

@Controller
public class WelcomeController {

    @ModelAttribute("phoneTypes")
    public Collection<PhoneType> populatePhoneTypes(){
        return Arrays.asList(PhoneType.values());
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @PostMapping("/welcome")
    public String welcomeMessage(@ModelAttribute("name") String name,@ModelAttribute("id") Integer id, Model model){
        model.addAttribute("message","Hello "+ name + " !!!");
        return "welcome";
    }

    @InitBinder
    public void initBinder(WebDataBinder webInitBinder){
       webInitBinder.setDisallowedFields("name");
    }

}
