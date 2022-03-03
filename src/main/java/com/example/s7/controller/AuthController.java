package com.example.s7.controller;

import com.example.s7.model.User;
import com.example.s7.service.Service;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Service service;

    public AuthController(Service service) {
        this.service = service;
    }



    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        User user = (principal instanceof User) ? (User) principal : null;
        return Objects.nonNull(user) ? this.service.getByLogin(user.getName()) : null;
    }
}