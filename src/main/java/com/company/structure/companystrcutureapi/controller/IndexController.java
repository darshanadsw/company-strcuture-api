package com.company.structure.companystrcutureapi.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@RestController
public class IndexController {

    @GetMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getIndexApi(){

        return ResponseEntity.ok().body("{timestamp : " + LocalDateTime.now()
            .format(DateTimeFormatter.ISO_DATE_TIME) +"}");
    }


}
