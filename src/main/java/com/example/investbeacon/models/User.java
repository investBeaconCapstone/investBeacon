package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 50)
    private String first_name;

    @Column(nullable = false, length = 50)
    private String last_name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "photo")
    private String profile_img;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ForumPost> forumPosts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn (name = "user_id")},
            inverseJoinColumns ={@JoinColumn(name = "follow_id")})
    private List<User> users;


    public User() {}

    public User(boolean isAdmin, String username, String first_name, String last_name, String email, String password, String profile_img, List<ForumPost> forumPosts, List<User> users) {
        this.isAdmin = isAdmin;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.profile_img = profile_img;
        this.forumPosts = forumPosts;
        this.users = users;
    }

    public User(String username, String first_name, String last_name, String email, String password, String profile_img, List<ForumPost> forumPosts, List<User> users) {
        this.username = username;
    }
}
