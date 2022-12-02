package com.aimcodes.fashionBlog.services.serviceImpl;


import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.entities.Like;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.repositories.CommentRepository;
import com.aimcodes.fashionBlog.repositories.LikeRepository;
import com.aimcodes.fashionBlog.services.LikeService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    public ResponseEntity<ApiResponse> createLike(Long comment_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        Like like = new Like();
        like.setComment(comment);
        if(user != null){
            like.setUser(user);
        }
        likeRepository.save(like);
        return new ResponseEntity<>(new ResponseManager().successfulRequest(like), HttpStatus.CREATED);
    }


    @Override
    public ApiResponse unlike(Long like_id){
        Like like = likeRepository.findById(like_id).orElse(null);
        likeRepository.delete(like);

        return new ResponseManager().successfulRequest("deleted!");
    }

}
