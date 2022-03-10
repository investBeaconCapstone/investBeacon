package com.example.investbeacon.models;

import javax.persistence.*;

@Entity
@Table(name = "forum_posts_likes")
public class ForumPostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long like_id;
    private Like like;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "forum_posts_id", referencedColumnName = "id")
    private ForumPost post;


}
