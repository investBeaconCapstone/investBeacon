package com.example.investbeacon.controllers;

import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private UserRepository userDao;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        userDao.save(user);
        return "redirect:/login";
    }
}
