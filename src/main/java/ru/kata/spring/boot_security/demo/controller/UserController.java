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

//        service.save(new User("user","user"));
//        User admin = new User("admin","admin");
//        service.save(admin);




//    @GetMapping("/get")
//    public String getUser(ModelMap model){
//        model.addAttribute("users",service.getUsers());
//        return "getall";
//    }
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id")int id, Model model){
//        model.addAttribute("user",service.show(id));
//        return "show";
//    }

    @GetMapping("")
    public String user (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Details userDetails = (Details) authentication.getPrincipal();
        model.addAttribute("user", service.show(userDetails.getUser().getId()));
        return "show";
    }

}
//    @GetMapping("/new")
//    public String newUser(@ModelAttribute("user") User user){
//        return "newUser";
//    }
//    @PostMapping()
//    public String create(@ModelAttribute("user") User user, BindingResult bindingResult){
//        if(bindingResult.hasErrors()) {
//            return "newUser";
//        }
//        service.save(user);
//        return "redirect:users/get";
//    }
//    @GetMapping("/{id}/edit")
//    public String edit(Model model,@PathVariable("id")int id){
//        model.addAttribute("user", service.show(id));
//        return "editUser";
//    }
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user")  User user,BindingResult bindingResult,@PathVariable("id") int id){
//        if(bindingResult.hasErrors()){
//            return "editUser";
//        }
//        service.update(id,user);
//        return "redirect:/users/get";
//    }
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id")int id){
//        service.delete(id);
//        return "redirect:/users/get";
//    }
