package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Authority;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.AuthorityRepository;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetupLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder encoder;

    @Autowired

    public SetupLoader(AuthorityRepository authorityRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Authority readAuthority
                = createAuthorityIfNotFound("READ_PRIVILEGE");
        Authority writeAuthority
                = createAuthorityIfNotFound("WRITE_PRIVILEGE");

        List<Authority> adminAuthorities = Arrays.asList(
                readAuthority, writeAuthority);
        createRoleIfNotFound("ROLE_ADMIN", adminAuthorities);
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readAuthority));
        Role userRole = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setUsername("user");
        user.setPassword(encoder.encode("user"));
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setRoles(List.of(adminRole, userRole));
        userRepository.save(admin);

        alreadySetup = true;
    }

    @Transactional
    Authority createAuthorityIfNotFound(String name) {

        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Authority> authorities) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}