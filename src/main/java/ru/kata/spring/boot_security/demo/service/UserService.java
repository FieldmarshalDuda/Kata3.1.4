package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getUsers();
    User show(int id);
    void save(User user);
    void update(int id,User user);
    void delete(int id);
    Set<Role> getRole();
}
