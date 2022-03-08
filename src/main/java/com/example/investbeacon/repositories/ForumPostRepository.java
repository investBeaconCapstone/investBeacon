package com.example.investbeacon.repositories;

import com.example.investbeacon.models.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {


    ForumPost findPostById(long id);
}
