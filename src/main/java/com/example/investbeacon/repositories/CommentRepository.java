package com.example.investbeacon.repositories;

import com.example.investbeacon.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
