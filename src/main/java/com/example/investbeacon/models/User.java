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
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "photo")
    private String profileImg;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ForumPost> forumPosts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn (name = "user_id")},
            inverseJoinColumns ={@JoinColumn(name = "follow_id")})
    private List<User> users;


    public User() {}

    public User(boolean isAdmin, String username, String firstname, String lastname, String email, String password, String profileImg, List<ForumPost> forumPosts, List<User> users) {
        this.isAdmin = isAdmin;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.forumPosts = forumPosts;
        this.users = users;
    }

    public User(String username, String firstname, String lastname, String email, String password, String profileImg, List<ForumPost> forumPosts, List<User> users) {
        this.username = username;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        isAdmin = copy.isAdmin;
        username = copy.username;
        firstname = copy.firstname;
        lastname = copy.lastname;
        email = copy.email;
        password = copy.password;
        profileImg = copy.profileImg;
        forumPosts = copy.forumPosts;
        users = copy.users;
    }
}
