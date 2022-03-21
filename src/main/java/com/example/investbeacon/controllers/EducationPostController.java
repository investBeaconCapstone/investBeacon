package com.example.investbeacon.controllers;

import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.models.EducationPostLikes;
import com.example.investbeacon.models.User;
import com.example.investbeacon.repositories.CategoryRepository;
import com.example.investbeacon.repositories.EducationLikesRepository;
import com.example.investbeacon.repositories.EducationPostRepository;
import com.example.investbeacon.repositories.UserRepository;
import com.example.investbeacon.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Date;
import java.util.List;

@Controller
public class EducationPostController {
    private final EducationPostRepository postDao;
    private final UserRepository userDoa;
    private final CategoryRepository catDao;
    private final EducationLikesRepository likesDao;
    private final EmailService emailService;

    @Value("${FILESTACK_API_KEY}")
    String fileStackKey;

    public EducationPostController(EducationPostRepository postDao, UserRepository userDoa, CategoryRepository catDao, EmailService emailService, EducationLikesRepository likesDao) {
        this.postDao = postDao;
        this.userDoa = userDoa;
        this.catDao = catDao;
        this.emailService = emailService;
        this.likesDao = likesDao;
    }

    //Shows posts for specific category
    @GetMapping("/education/posts/{category}")
    public String postCatId(@PathVariable String category, Model model) {
        List<EducationPost> posts = catDao.findCategoryByCategory(category).getEducationPosts();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isCreator = false;
        boolean hasVoted = false;
//        Integer postLikes = null;

        for (EducationPost post : posts) {
//             postLikes  = post.getUserLikes().size();
//            System.out.println(postLikes);
            List<EducationPostLikes> likes = post.getUserLikes();

            if (post.getUser().getUsername().equals(user.getUsername())) {
                isCreator = true;
            }


            if (!likes.isEmpty()) {
                for (EducationPostLikes like : likes) {
                    if (like.getUser().getUsername().equals(user.getUsername())) {
                        hasVoted = true;
                        break;
                    }
                }
            }

        }


        model.addAttribute("creator", isCreator);
        model.addAttribute("voted", hasVoted);

        model.addAttribute("author", user);
        model.addAttribute("posts", posts);

        return "/education/show_category";
    }


    //upvote function
    @PostMapping("/education/posts/{category}/{id}/upvote")
    public String upPost(@PathVariable long id) {

        EducationPost likedPost = postDao.getById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EducationPostLikes likes = new EducationPostLikes(user, likedPost);


        likesDao.save(likes);
        return "redirect:/education/posts/{category}/";


    }

    //downvote function
    @PostMapping("/education/posts/{category}/{id}/downvote")
    public String downPost(@PathVariable long id) {


        EducationPost likedPost = postDao.getById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<EducationPostLikes> likes = likesDao.getEducationPostLikesByUser(user);
        for (EducationPostLikes like : likes) {
            if (like.getEdPost().getId() == likedPost.getId()) {
                System.out.println("like found");
                likesDao.deleteById(like.getId());
            }

        }

        return "redirect:/education/posts/{category}/";
    }

    //view create form
    @GetMapping("/education/posts/create")
    public String viewCreate(Model model) {

        model.addAttribute("post", new EducationPost());
        model.addAttribute("FILESTACK_API_KEY", fileStackKey);
        model.addAttribute("cat", catDao.findAll());

        return "/education/create";
    }


    //create a post for education
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

    //view edit form
    @GetMapping("/education/posts/{category}/{id}/edit")
    public String viewEdit(@PathVariable long id, Model model) {
        EducationPost editPost = postDao.getById(id);

        if (editPost.getUser().isAdmin()) {
            model.addAttribute("cat", catDao.findAll());
            model.addAttribute("post", editPost);
            return "/education/edit";
        } else {
            return "redirect:/login";
        }
    }

    //edit post and redirect to individual post
    @PostMapping("/education/posts/{category}/{id}/edit")
    public String postEdit(@PathVariable long id, @ModelAttribute EducationPost post) {


        if (postDao.getById(id).getUser().isAdmin()) {
            post.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            post.setCreatedDate(new Date());
            postDao.save(post);

        }

        return "redirect:/education/posts/{category}/{id}";

    }

    //delete post
    @PostMapping("/education/posts/{category}/{id}/delete")
    public String postDelete(@PathVariable long id) {
        String postCat = postDao.getById(id).getCategory().getCategory();
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
