package com.company.structure.companystrcutureapi.controller;

import com.company.structure.companystrcutureapi.command.EmployeeVO;
import com.company.structure.companystrcutureapi.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employee")
    public String employeePage(){
        return "employee";
    }

    @PostMapping("/employee")
    public String saveEmployee(@Valid @ModelAttribute EmployeeVO employee, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(e -> {
                log.error(e.getDefaultMessage());
            });
        }
        return "employee";
    }
}
