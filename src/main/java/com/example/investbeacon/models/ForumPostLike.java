package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ForumPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne @JoinColumn(name = "post_id")
    private ForumPost forumPost;

    public ForumPostLike() {
    }

    public ForumPostLike(User users, ForumPost forumPost) {
        this.users = users;
        this.forumPost = forumPost;
    }
}