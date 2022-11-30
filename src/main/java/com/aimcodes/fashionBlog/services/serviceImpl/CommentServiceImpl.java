package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import com.aimcodes.fashionBlog.repositories.CommentRepository;
import com.aimcodes.fashionBlog.repositories.PostRepository;
import com.aimcodes.fashionBlog.services.CommentService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ResponseManager responseManager;

    @Override
    public ApiResponse create_comment(CommentRequestDto request, Long post_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Post post = postRepository.findById(post_id).orElse(null);

        if(user != null){
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setContent(request.getContent());
            comment.setComment_author(user.getUsername());
            comment.setPost(post);

            commentRepository.save(comment);
            return responseManager.successfulRequest(comment);

        }else if(user == null && request.getComment_author() != null){
            Comment comment = new Comment();
            comment.setContent(request.getContent());
            comment.setComment_author(request.getComment_author());
            comment.setPost(post);

            commentRepository.save(comment);
            return responseManager.successfulRequest(comment);

        }

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setComment_author("Anonymous");
        comment.setPost(post);

        commentRepository.save(comment);
        return responseManager.successfulRequest(comment);

    }


    @Override
    public ApiResponse deleteComment(Long comment_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Comment comment = commentRepository.findById(comment_id).orElse(null);

        if(user.getId().equals(comment.getUser().getId()) || user.getRole().equals(Role.valueOf("ADMIN"))){
            commentRepository.delete(comment);
            return responseManager.successfulRequest("comment deleted successfully!");
        }
        return null;
    }


    @Override
    public ApiResponse view_comment(Long comment_id){
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        return responseManager.successfulRequest(comment);

    }


    @Override
    public List<Comment> view_all_comments(Long post_id){
        Post post = postRepository.findById(post_id).orElse(null);

        return post.getComments();
    }

}
