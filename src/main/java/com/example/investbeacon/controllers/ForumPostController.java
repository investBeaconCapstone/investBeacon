package com.example.investbeacon.controllers;

import com.example.investbeacon.models.Comment;
import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.CategoryRepository;
import com.example.investbeacon.repositories.CommentRepository;
import com.example.investbeacon.repositories.ForumPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.Optional;


@Controller
public class ForumPostController {
    private final ForumPostRepository forumPostDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;
    private final CommentRepository commentDao;


    public ForumPostController(ForumPostRepository forumPostDao, UserRepository userDao, CategoryRepository categoryDao, CommentRepository commentDao) {
        this.forumPostDao = forumPostDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.commentDao = commentDao;
    }

    //    VIEW ALL Forum Posts
    @GetMapping("/forum-posts")
    public String forumPosts(Model model) {
        model.addAttribute("allPosts", forumPostDao.findAll());
        return "/forum-posts/index";
    }

    //      VIEW CREATE Forum Post
    @GetMapping("/forum-posts/create")
    public String createForumPostForm(Model model) {
        model.addAttribute("post", new ForumPost());
        model.addAttribute("categoryList", categoryDao.findAll());
        return "/forum-posts/create";
    }

    //    POST CREATED Forum Post
    @PostMapping("/forum-posts/create")
    public String createForumPost(@ModelAttribute ForumPost post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);
        post.setCreatedDate(new Date());
        forumPostDao.save(post);
        return "redirect:/forum-posts";
    }

    //    VIEW SINGLE Forum Post
    @GetMapping("/forum-posts/{id}")
    public String singleForumPost(@PathVariable long id, Model model) {
        Optional<ForumPost> forumPost = forumPostDao.findById(id);
        if (forumPost.isPresent()) {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
                User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                model.addAttribute("loggedInUser", loggedInUser);
            }
            ForumPost currentForumPost = forumPost.get();
            Comment comment = new Comment();
            comment.setPost(currentForumPost);
            model.addAttribute("comment", comment);
            model.addAttribute("singleForumPost", currentForumPost);
            return "/forum-posts/single-post";
        } else {
            return "redirect:/forum-posts";
        }
    }

    //    VIEW EDIT Forum Post
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

    //    POST EDITED Forum Post
    @PostMapping("/forum-posts/{id}/edit")
    public String editForumPosts(@ModelAttribute ForumPost editPost, @PathVariable long id) {
        if (forumPostDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            editPost.setCreatedDate(forumPostDao.getById(id).getCreatedDate());
            if (editPost.getContentImageUrl() == null) {
                editPost.setContentImageUrl(forumPostDao.getById(id).getContentImageUrl());
            }
            if (editPost.getCategories().isEmpty()) {
                editPost.setCategories((forumPostDao.getById(id).getCategories()));
            }
            editPost.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            forumPostDao.save(editPost);
        }
        return "redirect:/forum-posts";
    }

    //    DELETE Forum Post
    @PostMapping("/forum-posts/{id}/delete")
    public String deleteForumPosts(@PathVariable long id) {
        ForumPost forumPost = forumPostDao.findPostById(id);
        User user = userDao.findByForumPosts(forumPost);
        User currentUser = userDao.getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (currentUser.getId() == user.getId()) {
            forumPostDao.delete(forumPost);
        }
        return "redirect:/forum-posts";
    }

    //     POST Comment
    @PostMapping("/forum-posts/{id}/comment")
    public String comment(@ModelAttribute Comment comment, @PathVariable long id, @ModelAttribute ForumPost post) {
        Comment newComment = new Comment();
        User currentUser = userDao.getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        newComment.setPost(forumPostDao.getById(id));
        newComment.setUser(currentUser);
        newComment.setCreateDate(new Date());
        newComment.setContent(comment.getContent());
        commentDao.save(newComment);
        return "redirect:/forum-posts/" + id;
    }

    //    VIEW Edit Comment Form
    @GetMapping("/forum-posts/{id}/comment/{commentId}/edit")
    public String viewComment(@PathVariable long id, @PathVariable long commentId, Model model) {
        Comment oldComment = commentDao.getById(commentId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oldComment.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("comment", oldComment);
            return "/forum-posts/edit-comment";
        } else {
            return "redirect:/forum-posts/" + id;
        }
    }

    //    POST EDITED Comment
    @PostMapping("/forum-posts/{id}/comment/{commentId}/edit")
    public String editComment(@ModelAttribute Comment comment, @PathVariable long commentId, @PathVariable long id) {
        Comment oldComment = commentDao.getById(commentId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oldComment.getUser().getId() == loggedInUser.getId()) {
//            comment.setId(oldComment.getId());
            comment.setUser(loggedInUser);
            comment.setPost(oldComment.getPost());
            comment.setCreateDate(new Date());
            commentDao.save(comment);
            return "redirect:/forum-posts/" + id;
        } else {
            return "redirect:/forum-posts";
        }
    }

    //  DELETE Comment
    @PostMapping("/forum-posts/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable long commentId, @PathVariable long id) {
        Comment comment = commentDao.getById(commentId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (comment.getUser().getId() == loggedInUser.getId()) {
            commentDao.deleteById(commentId);
        }
        return "redirect:/forum-posts/" + id;
    }


}
