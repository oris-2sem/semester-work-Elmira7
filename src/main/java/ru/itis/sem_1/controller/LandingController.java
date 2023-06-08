package ru.itis.sem_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping("/")
public class LandingController {

    @GetMapping
    public String landing(HttpServletRequest request){
        return "landing";
    }
}
