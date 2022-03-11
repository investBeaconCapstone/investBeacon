package com.example.investbeacon.controllers;

import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable long id, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User loggedInUser = userDao.getById(user.getId());
            model.addAttribute("loggedInUser", loggedInUser);
        }
        User user = userDao.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("thisUsersPosts", user.getForumPosts());
//        model.addAttribute("following", user.getUsers());
//        model.addAttribute("followedBy", user.getUsers());
        return "users/profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String viewEditProfile(@PathVariable long id, Model model) {
        User userToEdit = userDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userToEdit.getId() == loggedInUser.getId()) {
            model.addAttribute("userToEdit", userToEdit);
            return "/users/edit";
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping("/profile/{id}/edit")
    public String editProfile(@ModelAttribute User userToEdit, @PathVariable long id) {
        if (userDao.getById(id).getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            String hash = passwordEncoder.encode(userToEdit.getPassword());
            userToEdit.setPassword(hash);
            userDao.save(userToEdit);
        }
        return "redirect:/profile/{id}";
    }

    @PostMapping("/profile/{id}/delete")
    public String deleteProfile(@PathVariable long id) {
        if (userDao.getById(id).getId() == (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            userDao.deleteById(id);
        }
        return "redirect:/";
    }
}
