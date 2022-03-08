package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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
}
