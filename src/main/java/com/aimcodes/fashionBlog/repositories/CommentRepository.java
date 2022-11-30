package com.aimcodes.fashionBlog.repositories;

import com.aimcodes.fashionBlog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
