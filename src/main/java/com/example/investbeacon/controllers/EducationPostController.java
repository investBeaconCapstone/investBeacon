package com.example.investbeacon.controllers;

import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.repositories.CategoryRepository;
import com.example.investbeacon.repositories.EducationPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


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

    @GetMapping("/education/posts")
    public String posts(Model model) {
        List<EducationPost> allPosts = postDao.findAll();
        model.addAttribute("allPosts", allPosts);


        return "/education/education_posts";
    }

    @GetMapping("/education/posts/{category}")
    public String postCatId(@PathVariable String category, Model model) {
//       List<EducationPost> post = catDao.findCategoryByCategory(category).getPosts();

        return "/education/show_category";
    }

    @GetMapping("/education/posts/create")
    public String viewCreate(Model model) {
        model.addAttribute("post", new EducationPost());

        return "/education/create";
    }

    @PostMapping("/education/posts/create")
    public String postCreate(@ModelAttribute EducationPost post) {

        //add verication
        postDao.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/education/posts/{id}/edit")
    public String viewEdit(@PathVariable long id, Model model) {
        EducationPost editPost = postDao.getById(id);
        model.addAttribute("post", editPost);
            return "/posts/edit";





    }

    @PostMapping("/education/posts/{id}/edit")
    public String postEdit(@PathVariable long id, @ModelAttribute EducationPost post) {

        //will add authentication later
            postDao.save(post);



        return "redirect:/education/posts";
    }

    @PostMapping("/education/posts/{id}/delete")
    public String postDelete(@PathVariable long id) {

        //will add authentication later
            postDao.deleteById(id);


        return "redirect:/education/posts";
    }


}
