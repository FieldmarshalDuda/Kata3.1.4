package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority {

    public Authority() {
    }
    public Authority(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "authorities",fetch = FetchType.EAGER)
    private List<Role> roles;
}
