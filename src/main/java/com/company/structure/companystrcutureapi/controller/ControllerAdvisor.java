package com.company.structure.companystrcutureapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView whenError(RuntimeException e){
        ModelAndView view = new ModelAndView("error404");
        view.addObject("error",e.getMessage());
        return view;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationError(MethodArgumentNotValidException e){
        e.getBindingResult().getFieldErrors();
        return ResponseEntity.badRequest().build();
    }
}
