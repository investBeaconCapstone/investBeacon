package com.example.investbeacon.controllers;

import com.example.investbeacon.models.*;
import com.example.investbeacon.repositories.*;
import com.example.investbeacon.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;


@Controller
public class ForumPostController {
    private final ForumPostRepository forumPostDao;
    private final UserRepository userDao;
    private final CategoryRepository categoryDao;
    private final CommentRepository commentDao;
    private final EmailService emailService;


    @Value("${FILESTACK_API_KEY}")
    String fileStackKey;

    @Autowired
    public ForumPostController(ForumPostRepository forumPostDao, UserRepository userDao, CategoryRepository categoryDao, CommentRepository commentDao, EmailService emailService) {
        this.forumPostDao = forumPostDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
        this.commentDao = commentDao;
        this.emailService = emailService;

    }

    //    VIEW ALL Forum Posts
    @GetMapping("/forum-posts")
    public String forumPosts(Model model, @PageableDefault(value = 8) Pageable pageable) {
        List<ForumPost> posts = forumPostDao.findAll();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User loggedInUser = userDao.getById(user.getId());
            HashMap<Long, ForumPost> postsLiked = new HashMap<>();
            List<ForumPost> listOfPostLikedByUser = forumPostDao.findByUsersContains(user);
            for (ForumPost postLiked : listOfPostLikedByUser) {
                postsLiked.put(postLiked.getId(), postLiked);
                System.out.println("HashMap" + postsLiked);
            }
            model.addAttribute("userLikes", postsLiked);
            model.addAttribute("user", user);
        }
        model.addAttribute("allPosts", posts);
        model.addAttribute("page", forumPostDao.findAll(pageable));
        return "forum-posts/index";
    }

    //      VIEW CREATE Forum Post
    @GetMapping("/forum-posts/create")
    public String createForumPostForm(Model model) {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            model.addAttribute("post", new ForumPost());
            model.addAttribute("FILESTACK_API_KEY", fileStackKey);
            model.addAttribute("categoryList", categoryDao.findAll());
            return "forum-posts/create";

        } else {
            return "redirect:/login";
        }

    }

    //    POST CREATED Forum Post
    @PostMapping("/forum-posts/create")
    public String createForumPost(@Valid @ModelAttribute(value = "post") ForumPost post, Errors validate, Model model) {
        if (validate.hasErrors()) {
            model.addAttribute("FILESTACK_API_KEY", fileStackKey);
            model.addAttribute("categoryList", categoryDao.findAll());
            return "forum-posts/create";
        } else {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            post.setUser(user);
            post.setCreatedDate(new Date());
            forumPostDao.save(post);
            return "redirect:/forum-posts";
        }
    }

    //    VIEW SINGLE Forum Post
    @GetMapping("/forum-posts/{id}")
    public String singleForumPost(@PathVariable long id, Model model) {
        boolean hasVoted = false;
        Optional<ForumPost> forumPost = forumPostDao.findById(id);
        ForumPost currentForumPost = forumPost.get();
        if (forumPost.isPresent()) {
            Comment comment = new Comment();
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Integer postLikes = currentForumPost.getUsers().size();
                List<User> likes = currentForumPost.getUsers();
                if (!likes.isEmpty()) {
                    for (User like : likes) {
                        if (like.getUsername().equals(user.getUsername())) {
                            hasVoted = true;
                            break;
                        }
                    }
                }
                comment.setPost(currentForumPost);
                model.addAttribute("loggedInUser", user);
                model.addAttribute("voted", hasVoted);
                model.addAttribute("likes", postLikes);
            }
            model.addAttribute("singleForumPost", currentForumPost);
            model.addAttribute("comment", comment);
            return "forum-posts/single-post";
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
            model.addAttribute("category", categoryDao.findAll());
            model.addAttribute("FILESTACK_API_KEY", fileStackKey);
            model.addAttribute("editPost", editPost);
            return "forum-posts/edit";
        } else {
            return "redirect:/forum-posts";
        }
    }

    //    POST EDITED Forum Post
    @PostMapping("/forum-posts/{id}/edit")
    public String editForumPosts(@Valid @ModelAttribute(value="editPost") ForumPost editPost, Errors validate, @PathVariable long id, Model model) {
        if(validate.hasErrors()){
            model.addAttribute("FILESTACK_API_KEY", fileStackKey);
            model.addAttribute("categoryList", categoryDao.findAll());
            model.addAttribute("editPost", editPost);
            return "forum-posts/edit";
        }else if (forumPostDao.getById(id).getUser().getId() == ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()) {
            editPost.setCreatedDate(forumPostDao.getById(id).getCreatedDate());
            System.out.println("Description: " + editPost.getDescription());
            if (editPost.getContentImageUrl() == null) {
                editPost.setContentImageUrl(forumPostDao.getById(id).getContentImageUrl());
            }
            if (editPost.getCategories() == null) {
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
        long userId = user.getId();
        User currentUser = userDao.getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (currentUser.getId() == user.getId()) {
            forumPostDao.delete(forumPost);
        }
        return "redirect:/profile/" + userId;
    }

    //     POST Comment
    @PostMapping("/forum-posts/{id}/comment")
    public String comment(@Valid @ModelAttribute(value = "comment") Comment comment, BindingResult result, @PathVariable long id, @ModelAttribute(value = "post") ForumPost post, @RequestParam("url") String url) {
        if (!result.hasErrors()) {
            Comment newComment = new Comment();
            User currentUser = userDao.getById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
            newComment.setPost(forumPostDao.getById(id));
            newComment.setUser(currentUser);
            newComment.setCreateDate(new Date());
            newComment.setContent(comment.getContent());
            commentDao.save(newComment);
            emailService.prepareAndSendForumPost(forumPostDao.findPostById(id));
        }
        return "redirect:" + url;
    }

    //    VIEW Edit Comment Form
    @GetMapping("/forum-posts/{id}/comment/{commentId}/edit")
    public String viewComment(@PathVariable long id, @PathVariable long commentId, Model model) {
        Comment oldComment = commentDao.getById(commentId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oldComment.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("comment", oldComment);
            return "forum-posts/edit-comment";
        } else {
            return "redirect:/forum-posts/" + id;
        }
    }

    //    POST EDITED Comment
    @PostMapping("/forum-posts/{id}/comment/{commentId}/edit")
    public String editComment(@Valid @ModelAttribute(value = "comment") Comment comment, Errors validate, @PathVariable long commentId, @PathVariable long id) {
        if (!validate.hasErrors()) {
            Comment oldComment = commentDao.getById(commentId);
            User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (oldComment.getUser().getId() == loggedInUser.getId()) {
                comment.setUser(loggedInUser);
                comment.setPost(oldComment.getPost());
                comment.setCreateDate(new Date());
                commentDao.save(comment);
                return "redirect:/forum-posts/" + id;
            } else {
                return "redirect:/forum-posts";
            }
        }
        return "redirect:/forum-posts/" + id + "/comment/" + commentId + "/edit";
    }

    //  DELETE Comment
    @PostMapping("/forum-posts/{id}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable long commentId, @PathVariable long id) {
        System.out.println("comment id: " + commentId);
        Comment comment = commentDao.getById(commentId);
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (comment.getUser().getId() == loggedInUser.getId()) {
            commentDao.deleteById(commentId);
        }
        return "redirect:/forum-posts/" + id;
    }

    //LIKE AND UNLIKE POST
    @PostMapping("/forum-posts/{id}/like-unlike")
    public String upVote(@PathVariable long id, @RequestParam("voted") boolean voted, @RequestParam("url") String url) {
        ForumPost likedPost = forumPostDao.getById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (voted) {
            likedPost.getUsers().removeIf(n -> n.getId() == user.getId());
        } else {
            likedPost.getUsers().add(user);
        }
        forumPostDao.save(likedPost);
        return "redirect:" + url;
    }


}
