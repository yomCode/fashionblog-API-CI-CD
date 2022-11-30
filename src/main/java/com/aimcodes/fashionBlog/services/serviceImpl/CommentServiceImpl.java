package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import com.aimcodes.fashionBlog.pojos.CommentResponseDto;
import com.aimcodes.fashionBlog.repositories.CommentRepository;
import com.aimcodes.fashionBlog.repositories.PostRepository;
import com.aimcodes.fashionBlog.services.CommentService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ResponseManager responseManager;

    @Override
    public ResponseEntity<ApiResponse> create_comment(CommentRequestDto request, Long post_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Post post = postRepository.findById(post_id).orElse(null);

        if(user != null){
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setContent(request.getContent());
            comment.setComment_author(user.getUsername());
            comment.setPost(post);
            commentRepository.save(comment);

            CommentResponseDto response = new CommentResponseDto();
            response.setContent(comment.getContent());
            response.setAuthor(comment.getComment_author());

            return new ResponseEntity<>(responseManager.successfulRequest(comment), HttpStatus.ACCEPTED);

        }else if(user == null && request.getComment_author() != null){
            Comment comment = new Comment();
            comment.setContent(request.getContent());
            comment.setComment_author(request.getComment_author());
            comment.setPost(post);
            commentRepository.save(comment);

            CommentResponseDto response = new CommentResponseDto();
            response.setContent(comment.getContent());
            response.setAuthor(comment.getComment_author());

            return new ResponseEntity<>(responseManager.successfulRequest(comment), HttpStatus.ACCEPTED);

        }

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setComment_author("Anonymous");
        comment.setPost(post);
        commentRepository.save(comment);

        CommentResponseDto response = new CommentResponseDto();
        response.setContent(comment.getContent());
        response.setAuthor(comment.getComment_author());

        return new ResponseEntity<>(responseManager.successfulRequest(comment), HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<ApiResponse> deleteComment(Long comment_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        Comment comment = commentRepository.findById(comment_id).orElse(null);

        if(user.getId().equals(comment.getUser().getId()) || user.getRole().equals(Role.valueOf("ADMIN"))){
            commentRepository.delete(comment);


            return new ResponseEntity<>(responseManager.successfulRequest("comment deleted successfully!"), HttpStatus.ACCEPTED);
        }
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> view_comment(Long comment_id){
        Comment comment = commentRepository.findById(comment_id).orElse(null);

        CommentResponseDto response = new CommentResponseDto();
        assert comment != null;
        response.setContent(comment.getContent());
        response.setAuthor(comment.getComment_author());
        return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.FOUND);

    }


    @Override
    public ResponseEntity<ApiResponse> view_all_comments(Long post_id){
        Post post = postRepository.findById(post_id).orElse(null);

        List<Comment> comments = post.getComments();
        List<CommentResponseDto> responses = new ArrayList<>();
        comments.forEach(comment ->{
            CommentResponseDto response = new CommentResponseDto();
            response.setContent(comment.getContent());
            response.setAuthor(comment.getComment_author());
            responses.add(response);

        });
        return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.FOUND);
    }

}
