package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "forum_posts")
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean isEducational;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createdDate;

    @Column(name ="content_img_url", length = 500)
    private String contentImageUrl;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "forum_categories",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Categories> categories;


    public ForumPost() {
    }

    public ForumPost(boolean isEducational, String title, String description, Date createdDate, String contentImageUrl, User user, List<Categories> categories) {
        this.isEducational = isEducational;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.contentImageUrl = contentImageUrl;
        this.user = user;
        this.categories = categories;
    }


}
