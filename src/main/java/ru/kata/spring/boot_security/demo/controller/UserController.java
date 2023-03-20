package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.security.Details;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {


    private final UserService service;

    @Autowired
    public UserController( UserService service) {
        this.service=service;
    }

    @GetMapping("")
    public String user (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Details userDetails = (Details) authentication.getPrincipal();
        model.addAttribute("user", service.show(userDetails.getUser().getId()));
        return "show";
    }

}
