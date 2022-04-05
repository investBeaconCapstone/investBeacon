package com.example.investbeacon.repositories;

import com.example.investbeacon.models.ForumPost;
import com.example.investbeacon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByForumPosts(ForumPost forumPost);
    User findByResetPasswordToken(String token);
    User findByEmail(String email);
}
