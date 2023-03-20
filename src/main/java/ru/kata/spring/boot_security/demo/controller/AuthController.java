package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator validator;
    private final UserService service;

    public AuthController(UserValidator validator, UserService service) {
        this.validator = validator;
        this.service = service;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "registration";
    }
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user")@Valid User user,
                                      BindingResult bindingResult){
        validator.validate(user,bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        service.save(user);
        return "redirect:/auth/login";
    }
}
