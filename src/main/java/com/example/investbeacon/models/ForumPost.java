package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Post must have a title")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank(message = "Post must have a description")
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

    @ManyToMany()
    @JoinTable(name = "forum_categories",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "forum_posts_likes",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments;

    public ForumPost() {
    }

    public ForumPost(String title, String description, Date createdDate, String contentImageUrl, User user, List<Category> categories, List<User> users) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.contentImageUrl = contentImageUrl;
        this.user = user;
        this.categories = categories;
        this.users = users;
    }

    public ForumPost(ForumPost copy){
        id =  copy.id;
        title = copy.title;
        description = copy.description;
        createdDate = copy.createdDate;
        contentImageUrl = copy.contentImageUrl;
        user = copy.user;
        categories = copy.categories;
        users = copy.users;
    }

    @Override
    public String toString(){
        return "ForumPost{" +
                "title='" + title + '\'' +
                "description='" + description + '\'' +
                '}';
    }
}
