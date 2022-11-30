package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import com.aimcodes.fashionBlog.pojos.PostResponseDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.repositories.PostRepository;
import com.aimcodes.fashionBlog.services.PostService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ResponseManager responseManager;

    @Override
    public ResponseEntity<ApiResponse> createPost(PostRequestDto request, HttpSession session) {
        User user = (User) session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            Category category = categoryRepository.findByName(request.getCategory().toLowerCase());

            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setCategory(category);
            post.setUser(user);
            postRepository.save(post);

            PostResponseDto response = new PostResponseDto();
            response.setTitle(post.getTitle());
            response.setContent(post.getContent());
            response.setCategory(post.getCategory().getName());
            response.setCreated_date(post.getCreatedAt());

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED);
        }
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> edit_post(PostRequestDto request, Long post_id, HttpSession session){

        User user = (User) session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){
            Post post = postRepository.findById(post_id).orElseThrow(null);
            Category category = categoryRepository.findByName(request.getCategory());
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setCategory(category);
            postRepository.save(post);

            PostResponseDto response = new PostResponseDto();
            response.setTitle(post.getTitle());
            response.setContent(post.getContent());
            response.setCategory(post.getCategory().getName());

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
        }
        return null;
    }


    @Override
    public ResponseEntity<ApiResponse> delete_Post(Long post_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        if(user.getRole().equals(Role.valueOf("ADMIN"))){

            Post post = postRepository.findById(post_id).orElseThrow(null);
            postRepository.delete(post);

            return new ResponseEntity<>(responseManager.successfulRequest("Post deleted successfully!"), HttpStatus.ACCEPTED);
        }
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> view_all_post(){
        List<Post> posts = postRepository.findAll();

        List<PostResponseDto> responses = new ArrayList<>();

        posts.forEach(post -> {
            PostResponseDto response = new PostResponseDto();
            response.setTitle(post.getTitle());
            response.setContent(post.getContent());
            response.setCategory(post.getCategory().getName());
            response.setCreated_date(post.getCreatedAt());
            responses.add(response);

        });
        return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<ApiResponse> view_post_by_category(String categoryName){
        Category category = categoryRepository.findByName(categoryName);

        if(category != null){
            List<Post> posts = category.getPosts();
            List<PostResponseDto> responses = new ArrayList<>();
            posts.forEach(post -> {
                PostResponseDto response = new PostResponseDto();
                response.setTitle(post.getTitle());
                response.setContent(post.getContent());
                response.setCreated_date(post.getCreatedAt());
                responses.add(response);
            });
            return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.ACCEPTED);
        }
        return null;
    }

}
