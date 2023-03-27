package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.Details;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    public UserController() {
    }

    @GetMapping("")
    public String user(@AuthenticationPrincipal Details userdetails, Model model) {
        model.addAttribute("user", userdetails.getUser());
        model.addAttribute("roles", userdetails.getUser().getRoles());
        return "user";
    }

}
