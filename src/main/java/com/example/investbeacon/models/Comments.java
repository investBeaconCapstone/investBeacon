package com.example.investbeacon.models;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name="content", length=512)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private ForumPost post;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
