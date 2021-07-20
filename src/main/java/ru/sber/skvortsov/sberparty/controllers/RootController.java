package ru.sber.skvortsov.sberparty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class RootController {

    @GetMapping
    public String getSwaggerUI(){
        return "redirect:/swagger-ui.html";
    }
}
