package com.aimcodes.fashionBlog.services.serviceImpl;

import com.aimcodes.fashionBlog.entities.Category;
import com.aimcodes.fashionBlog.entities.Post;
import com.aimcodes.fashionBlog.entities.User;
import com.aimcodes.fashionBlog.enums.Role;
import com.aimcodes.fashionBlog.exceptions.HandleNullException;
import com.aimcodes.fashionBlog.exceptions.NoAccessException;
import com.aimcodes.fashionBlog.exceptions.NoDataFoundException;
import com.aimcodes.fashionBlog.pojos.ApiResponse;
import com.aimcodes.fashionBlog.pojos.PostRequestDto;
import com.aimcodes.fashionBlog.pojos.PostResponseDto;
import com.aimcodes.fashionBlog.repositories.CategoryRepository;
import com.aimcodes.fashionBlog.repositories.PostRepository;
import com.aimcodes.fashionBlog.services.PostService;
import com.aimcodes.fashionBlog.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final ResponseManager responseManager;

    @Override
    public ResponseEntity<ApiResponse> createPost(PostRequestDto request, HttpSession session) {
        User user = (User) session.getAttribute("currUser");
        if (user != null && user.getRole().equals(Role.valueOf("ADMIN"))) {
            Category category = categoryRepository.findByName(request.getCategory().toLowerCase());

//            Post post = modelMapper.map(request, Post.class);
            Post post = Post.builder().title(request.getTitle())
                            .content(request.getContent())
                                    .category(category)
                                            .user(user)
                                                .build();
//            post.setTitle(request.getTitle());
//            post.setContent(request.getContent());
//            post.setCategory(category);
//            post.setUser(user);
            postRepository.save(post);

//            PostResponseDto response = modelMapper.map(post, PostResponseDto.class);
            PostResponseDto response = PostResponseDto.builder()
                            .title(post.getTitle())
                                    .content(post.getContent())
                                            .created_date(post.getCreatedAt())
                                                    .category(post.getCategory().getName()).build();
//            response.setTitle(post.getTitle());
//            response.setContent(post.getContent());
//            response.setCreated_date(post.getCreatedAt());
//            response.setCategory(post.getCategory().getName());

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.CREATED);
        }else if (user == null)
            throw new HandleNullException("Invalid user request", "No user in session");
        throw new NoAccessException("Unauthorized user", "User doesnt have the right to create a post!");
    }


    @Override
    public ResponseEntity<ApiResponse> edit_post(PostRequestDto request, Long post_id, HttpSession session) {

        User user = (User) session.getAttribute("currUser");
        if (user != null && user.getRole().equals(Role.valueOf("ADMIN"))) {
            Post post = postRepository.findById(post_id).orElseThrow(null);

            if(post != null)
                post.setTitle(request.getTitle());
            assert post != null;
            post.setContent(request.getContent());
            postRepository.save(post);

            PostResponseDto response = PostResponseDto.builder()
                            .title(post.getTitle())
                                    .content(post.getContent())
                                            .created_date(post.getCreatedAt())
                                                    .category(post.getCategory().getName()).build();
//            response.setTitle(post.getTitle());
//            response.setContent(post.getContent());
//            response.setCreated_date(post.getCreatedAt());
//            response.setCategory(post.getCategory().getName());

            return new ResponseEntity<>(responseManager.successfulRequest(response), HttpStatus.ACCEPTED);
        }else if(user == null)
            throw new HandleNullException("Login to edit post", "No user is in session");
        throw new NoAccessException("Unauthorized user", "User doesnt have the right to edit this post!");
    }

    @Override
    public ResponseEntity<ApiResponse> delete_Post(Long post_id, HttpSession session){
        User user = (User) session.getAttribute("currUser");
        if(user != null && user.getRole().equals(Role.valueOf("ADMIN"))){
            Post post = postRepository.findById(post_id).get();
            postRepository.delete(post);

            return new ResponseEntity<>(
                    responseManager.successfulRequest("Post deleted successfully!"),
                    HttpStatus.ACCEPTED
            );
        }else if(user == null)
            throw new HandleNullException("Invalid user request", "No user in session");
        throw new NoAccessException("Unauthorized user", "User doesnt have the right to delete this post!");
    }

    @Override
    public ResponseEntity<ApiResponse> view_all_post(){
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> responses = new ArrayList<>();

        posts.forEach(post -> {
//            PostResponseDto response = modelMapper.map(post, PostResponseDto.class);
            PostResponseDto response = PostResponseDto.builder()
                    .title(post.getTitle())
                        .content(post.getContent())
                            .created_date(post.getCreatedAt())
                                .category(post.getCategory().getName()).build();
//            PostResponseDto response = new PostResponseDto();
//            response.setTitle(post.getTitle());
//            response.setCategory(post.getCategory().getName());
//            response.setContent(post.getContent());
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
//                PostResponseDto response = modelMapper.map(post, PostResponseDto.class);
                PostResponseDto response = PostResponseDto.builder()
                        .title(post.getTitle())
                            .content(post.getContent())
                                .created_date(post.getCreatedAt())
                                    .category(post.getCategory().getName()).build();
//                PostResponseDto response = new PostResponseDto();
//                response.setTitle(post.getTitle());
//                response.setContent(post.getContent());
//                response.setCreated_date(post.getCreatedAt());
//                response.setCategory(post.getCategory().getName());
                responses.add(response);
            });
            return new ResponseEntity<>(responseManager.successfulRequest(responses), HttpStatus.ACCEPTED);
        }
        throw new NoDataFoundException("No such category", categoryName + " does not exist in the database");
    }

}
