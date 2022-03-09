package com.example.investbeacon.controllers;

import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.ForumPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ForumPostController {
    private final ForumPostRepository forumPostDao;
    private final UserRepository userDao;


    public ForumPostController(ForumPostRepository forumPostDao, UserRepository userDao) {
        this.forumPostDao = forumPostDao;
        this.userDao = userDao;
    }

    @GetMapping("/forum-posts")
    public String forumPosts(Model model) {
        model.addAttribute("allPosts", forumPostDao.findAll());
        return "/forum-posts/index";
    }



    @GetMapping("/forum-posts/create")
    public String createForumPostForm(Model model) {
        model.addAttribute("post", new ForumPost());
        return "/forum-posts/create";
    }

    @PostMapping("/forum-posts/create")
    public String createForumPost(@ModelAttribute ForumPost post) {
        post.setUser( (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        forumPostDao.save(post);
        return "redirect:/forum-posts";
    }

    @GetMapping("/forum-posts/{id}")
    public String singleForumPost(@PathVariable long id, Model model) {
        model.addAttribute("singlePost", forumPostDao.findPostById(id));
        return "/forum-posts/single-post";
    }
    @GetMapping("/forum-posts/{id}/edit")
    public String viewEdit(@PathVariable long id, Model model) {
       ForumPost editPost = forumPostDao.getById(id);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (editPost.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("editPost", editPost);
            return "/forum-posts/edit";
        } else {
            return "redirect:/forum-posts";
        }
    }

    @PostMapping("/forum-posts/{id}/edit")
       public String editForumPosts(@ModelAttribute ForumPost editPost, @PathVariable long id) {
        if (forumPostDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            editPost.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            forumPostDao.save(editPost);
        }
        return "redirect:/forum-posts";
    }

        @GetMapping("/forum-posts/{id}/delete")
        public String deleteForumPosts ( @PathVariable long id){
            forumPostDao.deleteById(id);
            return "redirect:/forum-posts";
        }



}
