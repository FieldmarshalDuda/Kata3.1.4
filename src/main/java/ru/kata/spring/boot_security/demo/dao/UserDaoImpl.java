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

    public void save(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    public void update(int id, User user) {
        User UpdUser = show(id);
        UpdUser.setUsername(user.getUsername());
        UpdUser.setPassword(encoder.encode(user.getPassword()));
        if(!UpdUser.getRoles().contains("ROLE_ADMIN")){
            user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        }else {
            user.setRoles(UpdUser.getRoles());
        }
        entityManager.merge(UpdUser);
    }

    public void delete(int id) {
        try {
            entityManager.remove(show(id));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
