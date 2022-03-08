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

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
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
    public String editProfile() { //@PathVariable long id, Model model (will go into parenthesis)
//        User userToEdit = userDao.getById(id);
//        model.addAttribute("postToEdit", userToEdit);
        return "Edit profile view";
    }

}
