package ru.kata.spring.boot_security.demo.model;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "password",nullable = false)
    private String password;

    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){ this.roles.add(role);}

    @Override
    public String toString() {
        return ("Username:" + username + " / "+" / " + "Identification: " + id + " "+"Password:"+ password);
    }
}