package com.example.investbeacon.controllers;


import com.example.investbeacon.repositories.ForumPostRepository;
import com.example.investbeacon.repositories.LikeRepository;
import com.example.investbeacon.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LikeController {

    private final ForumPostRepository forumPostDao;
    private final UserRepository userDao;
    private final LikeRepository likeDao;

    public LikeController(ForumPostRepository forumPostDao, UserRepository userDao, LikeRepository likeDao) {
        this.forumPostDao = forumPostDao;
        this.userDao = userDao;
        this.likeDao = likeDao;
    }


//    @GetMapping


//    @PostMapping
//    public ResponseEntity<Void> like(@RequestBody VoteDto voteDto) {
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }








}