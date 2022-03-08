package com.example.investbeacon.controllers;

import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.ForumPostRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ForumPostController {
    private final ForumPostRepository forumPostDao;
//    private final UserRepository userDao;


    public ForumPostController(ForumPostRepository forumPostDao) {
        this.forumPostDao = forumPostDao;
//        this.userDao = userDao;
    }

    @GetMapping("/index")
    @ResponseBody
    public String forumPosts() {
        return "All the Forum Posts";
    }
    @GetMapping("/forum-posts")
    @ResponseBody
    public String post(Model model) {
        return "/forum-posts/index";
    }

    @GetMapping("/forum-posts/{id}")
    @ResponseBody
    public String singleForumPost(@PathVariable long id, Model model) {
        ForumPost oneForumPost = forumPostDao.findPostById(id);
        model.addAttribute("singlePost", oneForumPost);
        return "/posts/single-post";
    }

    @GetMapping("/forum-posts/create")
    @ResponseBody
    public String createForumPostForm(Model model) {
        return "View form for creating posts";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createForumPost() {
        return "Create the forum post";
    }

    @GetMapping("/posts/{id}/edit")
    @ResponseBody
    public String viewEdit() {
        return "Edit post view";
    }

    @PostMapping("/posts/{id}/edit")
    @ResponseBody
    public String editForumPosts() {
        return "form to edit post";
    }

    @GetMapping("/forum-posts/{id}/delete")
    @ResponseBody
    public String deleteForumPosts() {
        return "Delete the post";
    }



}
