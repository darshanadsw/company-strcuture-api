package com.company.structure.companystrcutureapi.controller;

import com.company.structure.companystrcutureapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequestMapping("/api")
@RestController
public class IndexController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getIndexApi(){

        return ResponseEntity.ok().body("{timestamp : " + LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME) +"}");
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployeeInfo(){
        return ResponseEntity.ok()
            .body(employeeService.getAllEmployees());
    }


}
