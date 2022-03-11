package com.example.investbeacon.repositories;

import com.example.investbeacon.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByPost_Id(long id);
}
