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
    private Boolean isAdmin;

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

    @Lob
    @Column(name = "photo", columnDefinition = "BLOB")
    private byte[] profile_img;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ForumPost> forumPosts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn (name = "user_id")},
            inverseJoinColumns ={@JoinColumn(name = "follow_id")})
    private List<User> users;


    public User() {}

    public User(Boolean isAdmin, String username, String first_name, String last_name, String email, String password, byte[] profile_img, List<ForumPost> forumPosts, List<User> users) {
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

    public User(String username, String first_name, String last_name, String email, String password, byte[] profile_img, List<ForumPost> forumPosts, List<User> users) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(byte[] profile_img) {
        this.profile_img = profile_img;
    }


    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    public void setForum_posts(List<ForumPost> forumPosts) {
        this.forumPosts = forumPosts;
        this.users = users;
    }
}
