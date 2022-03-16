package com.example.investbeacon.repositories;

import com.example.investbeacon.models.ForumPostLike;
import com.example.investbeacon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ForumLikesRepository extends JpaRepository <ForumPostLike, Long> {
    List<ForumPostLike> getForumPostLikeByUsers(User user);
}
