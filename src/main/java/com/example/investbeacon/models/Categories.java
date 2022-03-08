package com.example.investbeacon.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String category;

    @ManyToMany(mappedBy = "categories")
    private List<ForumPost> posts;

    public Categories() {
    }

    public Categories(long id, String category, List<ForumPost> posts) {
        this.id = id;
        this.category = category;
        this.posts = posts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ForumPost> getPosts() {
        return posts;
    }

    public void setPosts(List<ForumPost> posts) {
        this.posts = posts;
    }
}

