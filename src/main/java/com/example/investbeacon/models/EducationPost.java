package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;



import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
public class EducationPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false,  length = 1000)
    private String description;

    @Column(nullable = false, name = "created_date")
    private Timestamp createdDate;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn (name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "education_posts_likes",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user")}
    )
    private List<User> userLikes;

    public EducationPost() {
    }

    public EducationPost(String title, String description, Timestamp createdDate, String content, User user) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
        this.user = user;
    }


    public EducationPost(String title, String description, Timestamp createdDate, String content, Category category, User user, List<User> users) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
        this.category = category;
        this.user = user;
        this.userLikes = users;
    }
}
