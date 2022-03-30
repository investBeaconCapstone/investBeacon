package com.example.investbeacon.models;



import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(name="content", length=512)
    @NotBlank(message = "Comment must have content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private ForumPost post;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
    }

    public Comment(int id, String content, ForumPost post, Date createDate, User user) {
        this.id = id;

        this.content = content;
        this.post = post;
        this.createDate = createDate;
        this.user = user;
    }

}
