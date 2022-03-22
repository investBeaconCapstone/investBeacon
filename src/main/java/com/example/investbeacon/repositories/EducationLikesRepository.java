package com.example.investbeacon.repositories;

import com.example.investbeacon.models.EducationPost;
import com.example.investbeacon.models.EducationPostLikes;
import com.example.investbeacon.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationLikesRepository extends JpaRepository<EducationPostLikes, Long> {

    List<EducationPostLikes> getEducationPostLikesByUser(User user);
    List<EducationPostLikes> getEducationPostLikesByEdPost(EducationPost edPost);
}
