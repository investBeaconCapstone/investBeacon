package com.example.investbeacon.models;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class EducationPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Please add a title")
    @Size(min = 3, message = "The title must be at least 3 characters.")
    @Column(nullable = false, length = 100)
    private String title;

    @NotBlank(message = "Please add content to the form")
    @Column(nullable = false,  length = 3500)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date createdDate;

    @Column(name ="content_img_url", length = 500)
    private String contentImageUrl;

    @NotNull(message = "Please Select a Category")
    @ManyToOne
    @JoinColumn (name = "cat_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "edPost")
    private List<EducationPostLikes> userLikes;

    public EducationPost() {
    }

    public EducationPost(String title, String description, Date createdDate, String contentImageUrl, Category category, User user, List<EducationPostLikes> userLikes) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.contentImageUrl = contentImageUrl;
        this.category = category;
        this.user = user;
        this.userLikes = userLikes;
    }
}
