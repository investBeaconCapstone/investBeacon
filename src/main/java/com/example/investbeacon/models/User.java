package com.example.investbeacon.models;

import javax.persistence.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

@Entity
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

    @Column
    private ImageIcon profile_img;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<forum_post> forum_posts;

    public User() {}

    public User(long id, Boolean isAdmin, String username, String first_name, String last_name, String email, String password, ImageIcon profile_img, List<forum_post>) {
        this.id = id;
        this.isAdmin = isAdmin;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.profile_img = profile_img;
        this.forum_posts = forum_posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public ImageIcon getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(ImageIcon profile_img) {
        this.profile_img = profile_img;
    }

    public List<forum_post> getForum_posts() {
        return forum_posts;
    }

    public void setForum_posts(List<forum_post> forum_posts) {
        this.forum_posts = forum_posts;
    }
}
