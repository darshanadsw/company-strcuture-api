package com.company.structure.companystrcutureapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

    @GetMapping("/api")
    public ResponseEntity getIndexApi(){
        return ResponseEntity.ok().body("Content");
    }

}
