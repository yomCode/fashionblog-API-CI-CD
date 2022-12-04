package com.aimcodes.fashionBlog.services.serviceImpl;


import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.entities.Like;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.repositories.CommentRepository;
import com.aimcodes.fashionBlog.repositories.LikeRepository;
import com.aimcodes.fashionBlog.services.LikeService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public ResponseEntity<ApiResponse> createLike(String uuid, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Comment comment = commentRepository.findByUuid(uuid);

        if(user != null && comment != null){
            Like like = Like.builder()
                    .comment(comment)
                    .build();
            likeRepository.save(like);
            return new ResponseEntity<>(new ResponseManager().successfulRequest(like), HttpStatus.CREATED);
        } else if (user == null)
            throw new HandleNullException("Invalid user", "No user in session");
        throw new HandleNullException("Invalid comment", "Comment with id " +  uuid + " does not exist in database");
    }

    @Override
    public ApiResponse unlike(Long like_id){
        Like like = likeRepository.findById(like_id).orElse(null);
        likeRepository.delete(like);

        return new ResponseManager().successfulRequest("deleted!");
    }
}
