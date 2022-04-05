package com.example.investbeacon.controllers;

import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.UserRepository;
import com.example.investbeacon.services.EmailService;
import com.example.investbeacon.services.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

public class ForgotPasswordController {
    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ForgotPasswordController(UserRepository userDao, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void updateResetPassword(String token, String email) throws UserNotFoundException{
        User user = userDao.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userDao.save(user);
        } else {
            throw new UserNotFoundException("Could not find any users with email" + email);
        }
    }

    public User get(String resetPasswordToken) {
        return userDao.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(User user, String newPassword) {
        passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        user.setResetPasswordToken(null);
        userDao.save(user);
    }




    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("forgotPassword", "Forgot Password");
        return "redirect:/forgotpassword";
    }
}
