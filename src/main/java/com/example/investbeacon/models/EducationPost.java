package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;



import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
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

    @Column(nullable = false,  length = 3500)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdDate;

    @Column(name ="content_img_url", length = 500)
    private String contentImageUrl;

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
        this.contentImageUrl = content;
        this.user = user;
    }


    public EducationPost(String title, String description, Timestamp createdDate, String content, Category category, User user, List<User> users) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.contentImageUrl = content;
        this.category = category;
        this.user = user;
        this.userLikes = users;
    }
}
