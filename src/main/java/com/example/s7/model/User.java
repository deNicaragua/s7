package com.example.s7.model;

import com.example.s7.validators.ValidLogin;
import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @ValidLogin
    @Column(name = "login")
    private String login;
    @NotNull
    @Size(min = 6)
    @Column(name = "password")
    private String password;



    public User(){
    }

    public User(Integer userId, String name) {
        this.userId = userId;
        this.name = name;

    }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return userId;
    }

    public String getName() {return name;}

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
