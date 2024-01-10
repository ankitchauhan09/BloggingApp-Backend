package com.example.demo.services;

import com.example.demo.entities.Category;
import com.example.demo.entities.Post;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.PostDto;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.PostRepo;
import com.example.demo.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
//        post.setUser(this.modelMapper.map(postDto.getUser(), User.class));
//        post.setCategory(this.modelMapper.map(postDto.getCategory(), Category.class));
        System.out.println("This is the post  = " + post);
        return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return null;
    }

    @Override
    public List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Post> postPage = this.postRepo.findAll(pageable);
        List<Post> content = postPage.getContent();
        List<PostDto> postList = content.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postList;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        List<PostDto> postList = new ArrayList<>();
        User user = this.userRepo.findById(userId).get();
        this.postRepo.findByUser(user).forEach(post -> {
            postList.add(this.modelMapper.map(post, PostDto.class));
        });
        return postList;
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        List<PostDto> postList = new ArrayList<>();
        Category category = this.categoryRepo.findById(categoryId).get();
        this.postRepo.findByCategory(category).forEach(post -> {
            postList.add(this.modelMapper.map(post, PostDto.class));
        });
        return postList;
    }

    @Override
    public List<PostDto> getPostByKeyword(String keyword) {
        List<PostDto> postList = new ArrayList<>();
        this.postRepo.findPostByTitleContains(keyword).forEach(post -> {
            postList.add(this.modelMapper.map(post, PostDto.class));
        });
        return postList;
    }
}
