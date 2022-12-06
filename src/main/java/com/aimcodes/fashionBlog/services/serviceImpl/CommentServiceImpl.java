package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Comment;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.exceptions.NoAccessException;
import com.aimcodes.fashionBlog.exceptions.NoDataFoundException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.CommentRequestDto;
import com.aimcodes.fashionBlog.pojos.CommentResponseDto;
import com.aimcodes.fashionBlog.repositories.CommentRepository;
import com.aimcodes.fashionBlog.repositories.PostRepository;
import com.aimcodes.fashionBlog.services.CommentService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import com.aimcodes.fashionBlog.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    @Autowired
    private HttpSession session;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ResponseManager responseManager;
    private final UuidGenerator uuidGenerator;

    @Override
    public ResponseEntity<ApiResponse> create_comment(CommentRequestDto request, String uuid){
        User user = (User) session.getAttribute("currUser");
        Post post = postRepository.findByUuid(uuid).orElseThrow(()->
                new NoDataFoundException("No such post", "Post with uuid " + uuid + " does not exist"));

        if(user != null){
            String uuidGen = uuidGenerator.generateUuid();
            Comment comment = Comment.builder()
                    .content(request.getContent())
                    .user(user)
                    .post(post)
                    .comment_author(user.getUsername())
                    .uuid(uuidGen)
                    .build();
            commentRepository.save(comment);

            CommentResponseDto response = CommentResponseDto.builder()
                    .content(comment.getContent())
                    .author(comment.getComment_author())
                    .build();

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);

        }else if(user == null && request.getComment_author() != null){
            Comment comment = Comment.builder()
                    .content(request.getContent())
                    .comment_author(request.getComment_author())
                    .post(post).build();
            commentRepository.save(comment);

            CommentResponseDto response = CommentResponseDto.builder()
                    .content(comment.getContent())
                    .author(comment.getComment_author())
                    .build();

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                        .comment_author("Anonymous")
                                .post(post)
                                    .build();
        commentRepository.save(comment);

        CommentResponseDto response = CommentResponseDto.builder()
                .content(comment.getContent())
                .author(comment.getComment_author())
                .build();
        if(post == null)
            throw new HandleNullException("Invalid post", "Post with id " + uuid + " does not exist in the database");
        return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse> deleteComment(String uuid){
        User user = (User) session.getAttribute("currUser");
        Comment comment = commentRepository.findByUuid(uuid);

        if(user != null && user.getRole().equals(Role.valueOf("ADMIN")) && comment != null){
            commentRepository.delete(comment);
            return new ResponseEntity<>(responseManager.successfulRequest("comment deleted successfully!"), HttpStatus.ACCEPTED);
        } else if (user == null)
            throw new HandleNullException("Invalid user", "No user in session");
        else if(comment == null)
             throw new HandleNullException("Comment unavailable", "Comment with id " + uuid + " does not exist in the database");
        throw new NoAccessException("No access to delete comment", "wrong user or user is not an admin");
    }


    @Override
    public ResponseEntity<ApiResponse> view_comment(String uuid){
        Comment comment = commentRepository.findByUuid(uuid);

        if(comment != null) {
            CommentResponseDto response = CommentResponseDto.builder()
                    .author(comment.getComment_author())
                    .content(comment.getContent())
                    .likes(comment.getLikes().size())
                    .build();
            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.FOUND);
        }
        throw new HandleNullException("Comment unavailable", "Comment with id " + uuid + " does not exist in the database");
    }


    @Override
    public ResponseEntity<ApiResponse> view_all_comments(String uuid){
        Post post = postRepository.findByUuid(uuid).orElseThrow(()->
                new NoDataFoundException("No such post", "Post with uuid " + uuid + " does not exist"));

        if (post != null) {
            List<Comment> comments = post.getComments();
            List<CommentResponseDto> responses = new ArrayList<>();
            comments.forEach(comment -> {
                CommentResponseDto response = CommentResponseDto.builder()
                        .author(comment.getComment_author())
                        .content(comment.getContent())
                        .likes(comment.getLikes().size())
                        .build();
                responses.add(response);

            });
            return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.FOUND);
        }
        throw new HandleNullException("Invalid post", "Post with id " + uuid + " does not exist in the database");
    }

}
