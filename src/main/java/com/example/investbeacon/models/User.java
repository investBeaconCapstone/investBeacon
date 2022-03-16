package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(name = "photo")
    private String profileImg;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ForumPost> forumPosts;

    //education post likes
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<EducationPostLikes> likes;
    //forum post likes
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<ForumPostLike> forumLikes;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn (name = "followee")},
            inverseJoinColumns ={@JoinColumn(name = "follower")})
    private List<User> users;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn(name = "follower")},
            inverseJoinColumns = {@JoinColumn(name = "followee")})
    private List<User> followers;


    public User() {}

    public User(boolean isAdmin, String username, String firstName, String lastName, String email, String password, String profileImg, List<ForumPost> forumPosts, List<User> users) {
        this.isAdmin = isAdmin;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.profileImg = profileImg;
        this.forumPosts = forumPosts;
        this.users = users;
    }

    public User(String username, String firstName, String lastName, String email, String password, String profileImg, List<ForumPost> forumPosts, List<User> users) {
        this.username = username;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        isAdmin = copy.isAdmin;
        username = copy.username;
        firstName = copy.firstName;
        lastName = copy.lastName;
        email = copy.email;
        password = copy.password;
        profileImg = copy.profileImg;
        forumPosts = copy.forumPosts;
        users = copy.users;
    }
}
