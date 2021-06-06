package com.example.desafioSpring.repositories;

import com.example.desafioSpring.models.Post;
import com.example.desafioSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query(value = "SELECT * FROM USER where ID=?1", nativeQuery = true)
    User findByUserId(Long userId);
}
