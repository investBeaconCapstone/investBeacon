package com.example.investbeacon.controllers;

import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        String unhash = user.getPassword();
        System.out.println(unhash);
        System.out.println(passwordEncoder.encode(unhash));
        String hash = passwordEncoder.encode(user.getPassword());
        System.out.println(hash);
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    @ResponseBody
    public String showProfile() {
        return "view profile";
    }

    @GetMapping("/profile/{id}/edit")
    @ResponseBody
    public String editProfile(@PathVariable long id, Model model) {
        User userToEdit = userDao.getById(id);
        model.addAttribute("postToEdit", userToEdit);
        return "Edit profile view";
    }

}
