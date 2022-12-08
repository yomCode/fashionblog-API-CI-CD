package com.aimcodes.fashionBlog.repositories;

import com.aimcodes.fashionBlog.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByUuid(String uuid);

    @Query(nativeQuery = true, value= "select * from post where title like %:question% or content like %:question%")
    Optional<List<Post>> findBySearch(String question);

}
