package com.example.restfulwebservices.repository;

import com.example.restfulwebservices.user.Post;
import com.example.restfulwebservices.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

// What is it managing + type of id
public interface PostRepository extends JpaRepository<Post, Integer> {
}
