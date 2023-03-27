package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

@Component
public class UserValidator implements Validator {
    private final UserDetailService service;

    @Autowired
    public UserValidator(UserDetailService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            service.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException e) {
            return; // пользователь не найден
        }
        errors.rejectValue("username", "", "Человек с таким именем уже существует");
    }
}
