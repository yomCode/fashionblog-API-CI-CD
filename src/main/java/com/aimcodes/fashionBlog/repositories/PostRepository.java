package com.aimcodes.fashionBlog.repositories;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    ResponseEntity<List<ApiResponse>> findByCategory(Category category);
    Post findByUuid(String uuid);

}
