package com.example.investbeacon.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class EducationPostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne @JoinColumn(name = "post_id")
    private EducationPost edPost;

    public EducationPostLikes() {
    }

    public EducationPostLikes(User user, EducationPost edPost) {
        this.user = user;
        this.edPost = edPost;
    }
}
