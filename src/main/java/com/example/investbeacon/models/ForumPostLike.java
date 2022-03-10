package com.example.investbeacon.models;

import javax.persistence.*;

@Entity
@Table(name = "forum_posts_likes")
public class ForumPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte like;

}
