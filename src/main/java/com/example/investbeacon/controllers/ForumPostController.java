package com.example.investbeacon.controllers;

import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.repositories.ForumPostRepository;
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

    @GetMapping("/forum-posts")
    public String forumPosts(Model model) {
        List<ForumPost> allForumPosts = forumPostDao.findAll();
        model.addAttribute("allForumPost", allForumPosts);
        return "/forum-posts/index";
    }

    @GetMapping("/forum-posts/{id}")
    public String singleForumPost(@PathVariable long id, Model model) {
        ForumPost singleForumPost = forumPostDao.findPostById(id);
        model.addAttribute("singlePost", singleForumPost);
        return "/forum-posts/single-post";
    }

    @GetMapping("/forum-posts/create")
    public String createForumPostForm(Model model) {
        model.addAttribute("post", new ForumPost());
        return "/forum-posts/create";
    }

    @PostMapping("/forum-posts/create")
    public String createForumPost(@ModelAttribute ForumPost post) {
        forumPostDao.save(post);
        return "redirect:/forum-posts";
    }

    @GetMapping("/forum-posts/{id}/edit")
    public String viewEdit(@PathVariable long id, Model model) {
        ForumPost editPost = forumPostDao.getById(id);
        model.addAttribute("post", editPost);
        return "/posts/edit";
    }

    @PostMapping("/forum-posts/{id}/edit")
       public String editForumPosts(@PathVariable long id) {
        ForumPost editedPost = new ForumPost(id);
        forumPostDao.save(editedPost);
        return "redirect:/forum-posts";
    }

    @GetMapping("/forum-posts/{id}/delete")
       public String deleteForumPosts(@PathVariable long id) {
        forumPostDao.deleteById(id);
        return "redirect:/forum-posts";
    }



}
