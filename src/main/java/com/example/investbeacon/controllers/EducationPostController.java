package com.example.investbeacon.controllers;

import com.example.investbeacon.models.Category;
import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.models.EducationPostLikes;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.CategoryRepository;
import com.example.investbeacon.repositories.EducationLikesRepository;
import com.example.investbeacon.repositories.EducationPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import com.example.investbeacon.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;


import java.util.Date;
import java.util.List;

@Controller
public class EducationPostController {
    private final EducationPostRepository postDao;
    private final UserRepository userDoa;
    private final CategoryRepository catDao;
    private final EducationLikesRepository likesDao;
    private final EmailService emailService;

    public EducationPostController(EducationPostRepository postDao, UserRepository userDoa, CategoryRepository catDao, EmailService emailService, EducationLikesRepository likesDao) {
        this.postDao = postDao;
        this.userDoa = userDoa;
        this.catDao = catDao;
        this.emailService = emailService;
        this.likesDao = likesDao;
    }


    @GetMapping("/education/posts/{category}")
    public String postCatId(@PathVariable String category, Model model) {
        List<EducationPost> post = catDao.findCategoryByCategory(category).getEducationPosts();

        model.addAttribute("posts", post);

        return "/education/show_category";
    }

    @GetMapping("/education/posts/{category}/{id}")
    public String singleEdPost(@PathVariable Long id, Model model) {
        EducationPost post = postDao.getById(id);

        Integer likes = post.getUserLikes().size();
        if (likes == null) {
            likes = 0;
        }

        model.addAttribute("likes", likes);
        model.addAttribute("post", post);

        return "/education/single_post";
    }

    @PostMapping("/education/posts/{category}/{id}/upvote")
    public String likePost(@PathVariable long id) {

        EducationPost likedPost = postDao.getById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        EducationPostLikes likes = new EducationPostLikes(user, likedPost);

        likesDao.save(likes);
        return "redirect:/education/posts/{category}/{id}";


    }

    @GetMapping("/education/posts/create")
    public String viewCreate(Model model) {

        model.addAttribute("post", new EducationPost());
        model.addAttribute("cat", catDao.findAll());

        return "/education/create";
    }

    @PostMapping("/education/posts/create")
    public String postCreate(@ModelAttribute EducationPost post) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(user);

        post.setCreatedDate(new Date());
        postDao.save(post);
        String subject = "New Post!";
        String body = "A new post was created by " + user.getUsername();
        emailService.prepareAndSendEdPost(post, subject, body);

        switch (post.getCategory().getCategory()) {
            case "Crypto":
                return "redirect:/education/posts/Crypto";
            case "Stocks":
                return "redirect:/education/posts/Stocks";
            case "Finance":
                return "redirect:/education/posts/Finance";
            case "Strategies":
                return "redirect:/education/posts/Strategies";
            default:
                return "redirect:/education/posts/Platforms";
        }

    }

    @GetMapping("/education/posts/{category}/{id}/edit")
    public String viewEdit(@PathVariable long id, Model model) {
        EducationPost editPost = postDao.getById(id);

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (editPost.getUser().getId() == loggedInUser.getId()) {
            model.addAttribute("cat", catDao.findAll());
            model.addAttribute("post", editPost);
            return "/education/edit";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/education/posts/{category}/{id}/edit")
    public String postEdit(@PathVariable long id, @ModelAttribute EducationPost post) {


        if (postDao.getById(id).getUser().getId() == (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            post.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            post.setCreatedDate(new Date());
            postDao.save(post);

        }

        return "redirect:/education/posts/{category}/{id}";

    }

    @PostMapping("/education/posts/{category}/{id}/delete")
    public String postDelete(@PathVariable long id, @PathVariable String category) {
        String postCat = postDao.getById(id).getCategory().getCategory();
        category = postCat;
        if (postDao.getById(id).getUser().getId() == (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            postDao.deleteById(id);
        }
        switch (postCat) {
            case "Crypto":
                return "redirect:/education/posts/Crypto";
            case "Stocks":
                return "redirect:/education/posts/Stocks";
            case "Finance":
                return "redirect:/education/posts/Finance";
            case "Strategies":
                return "redirect:/education/posts/Strategies";
            default:
                return "redirect:/education/posts/Platforms";
        }
    }


}
