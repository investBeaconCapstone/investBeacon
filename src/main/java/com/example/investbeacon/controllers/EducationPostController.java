package com.example.investbeacon.controllers;

import com.example.investbeacon.models.Category;
import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.CategoryRepository;
import com.example.investbeacon.repositories.EducationPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EducationPostController {
    private final EducationPostRepository postDao;
    private final UserRepository userDoa;
    private final CategoryRepository catDao;

    public EducationPostController(EducationPostRepository postDao, UserRepository userDoa, CategoryRepository catDao) {
        this.postDao = postDao;
        this.userDoa = userDoa;
        this.catDao = catDao;
    }


    @GetMapping("/education/posts/{category}")
    public String postCatId(@PathVariable String category, Model model) {
        List<EducationPost> post = catDao.findCategoryByCategory(category).getEducationPosts();
        model.addAttribute("posts", post);

        return "/education/show_category";
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

        switch (post.getCategory().getCategory()){
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

    @GetMapping("/education/posts/{id}/edit")
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

    @PostMapping("/education/posts/{id}/edit")
    public String postEdit(@PathVariable long id, @ModelAttribute EducationPost post) {



        if (postDao.getById(id).getUser().getId() == (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            post.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            post.setCreatedDate(new Date());
            postDao.save(post);

        }
        switch (post.getCategory().getCategory()){
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

    @PostMapping("/education/posts/{id}/delete")
    public String postDelete(@PathVariable long id) {
        if (postDao.getById(id).getUser().getId() == (((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())) {
            postDao.deleteById(id);
        }
        return "redirect:/";
    }




}
