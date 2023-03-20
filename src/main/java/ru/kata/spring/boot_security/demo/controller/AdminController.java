package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService service;
    @Autowired

    public AdminController(UserService service) {
        this.service = service;
    }
    @GetMapping("")
    public String adminPage(){

        return"admin";
    }
    @GetMapping("/get")
    public String userList(Model model) {
        model.addAttribute("allUsers", service.getUsers());
        return "getall";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")int id){
        service.delete(id);
        return "redirect:/admin/get";
    }
        @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "editUser";
        }
        service.update(id,user);
        return "redirect:/admin/get";
    }
        @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model){
        model.addAttribute("user",service.show(id));
        return "show";
    }
        @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user){
        return "newUser";
    }
        @PostMapping()
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "newUser";
        }
        service.save(user);
        return "redirect:admin/get";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable("id")int id){
        model.addAttribute("user", service.show(id));
        return "editUser";
    }
}
