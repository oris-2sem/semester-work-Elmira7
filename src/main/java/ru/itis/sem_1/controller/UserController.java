package ru.itis.sem_1.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import ru.itis.sem_1.dto.UserRequest;
import ru.itis.sem_1.dto.UserSignRequest;
import ru.itis.sem_1.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/profile")
    public String getUserYourself(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "profile";
    }

    @GetMapping("/profile/{userId}")
    public String getUser(@PathVariable UUID userId){
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String getUpdateUser(Principal principal, Model model){
        model.addAttribute("userRequest", new UserRequest());
        model.addAttribute("user", userService.getUserByEmail(principal.getName()));
        return "profileEdit";
    }

    @PostMapping("/profile/edit")
    public String updateUser(@Valid UserRequest userRequest,
                             Principal principal,
                             BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "profileEdit";
        }
        userRequest.setEmail(principal.getName());
        userService.updateUser(userRequest);
        return "redirect:/user/profile";
    }

    @PostMapping("/registration")
    public String registration(@Valid UserSignRequest userSignRequest,
                               BindingResult result){

        if (result.hasErrors()){
            return "redirect:/user/registration?error=true";
        }

        userService.createUser(userSignRequest);
        return "redirect:/user/login";
    }

    @GetMapping("/registration")
    public String toRegistrationForm(Model model){
        model.addAttribute("userSignRequest", new UserSignRequest());
        return "signUp";
    }


    @GetMapping("/login")
    public String toLoginForm(Model model){
        model.addAttribute("userSignRequest", new UserSignRequest());
        return "signIn";
    }
}
