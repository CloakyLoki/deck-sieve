package com.cloakyloki.http.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BindException.class)
    public String handleExceptions(BindingResult exception, Model model) {
        log.error("Incorrect Input Data", exception);
        model.addAttribute("errors", exception.getAllErrors());
        return "errorview/error500";
    }


}
