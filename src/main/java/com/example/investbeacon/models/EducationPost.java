package com.example.investbeacon.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
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

    @Lob
    @Column(name = "content", columnDefinition = "BLOB")
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "education_posts_likes",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    public EducationPost() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public EducationPost(String title, String description, Timestamp createdDate, byte[] content, User user) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
        this.user = user;
    }

    public EducationPost(String title, String description, Timestamp createdDate, byte[] content, User user, List<User> users) {
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
        this.user = user;
        this.users = users;
    }

    public EducationPost(long id, String title, String description, Timestamp createdDate, byte[] content, User user, List<User> users) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.content = content;
        this.user = user;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
