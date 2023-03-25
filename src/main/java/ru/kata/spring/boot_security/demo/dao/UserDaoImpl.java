package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.*;

import java.util.Collections;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder encoder, EntityManager entityManager, RoleRepository roleRepository) {
        this.encoder = encoder;
        this.entityManager = entityManager;
        this.roleRepository = roleRepository;
    }


    public List<User> getUserList() {
        return entityManager.createQuery("From User", User.class).getResultList();
    }

    public User show(int id) {
        return entityManager.createQuery("from User where id=:i", User.class)
                .setParameter("i", id).getSingleResult();
    }

    @Override

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        if (user.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        }
       entityManager.persist(user);
    }

    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
        if (updatedUser.getRoles().contains(roleRepository.findByName("ROLE_ADMIN"))) {
            updatedUser.getRoles().add(roleRepository.findByName("ROLE_USER"));
        }
        entityManager.merge(updatedUser);
    }

    public void delete(int id) {
        try {
            entityManager.remove(show(id));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
