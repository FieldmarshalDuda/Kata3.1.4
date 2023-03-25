package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserDao dao, RoleRepository roleRepository){
        userDao=dao;
        this.roleRepository=roleRepository;
    }


    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void update(int id,User user) {userDao.update(id, user);}

    @Override
    public List<User> getUsers() {
        return userDao.getUserList();
    }

    @Override
    public User show(int id) {
        return userDao.show(id);
    }

    @Override
    public Set<Role> getRole() {
        return new HashSet<>(roleRepository.findAll());
    }
}