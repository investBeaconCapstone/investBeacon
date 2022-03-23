package com.example.investbeacon.repositories;

import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    ForumPost findPostById(long id);
    List<ForumPost> findByUsersContains(User user);

}
