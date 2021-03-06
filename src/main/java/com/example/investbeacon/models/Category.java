package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<EducationPost> educationPosts;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    private List<ForumPost> posts;

    public Category() {
    }

    public Category(String category, List<EducationPost> educationPosts, List<ForumPost> posts) {
        this.category = category;
        this.educationPosts = educationPosts;
        this.posts = posts;
    }
}
