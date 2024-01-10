package com.example.demo.services;

import com.example.demo.payloads.PostDto;
import java.util.List;

public interface PostService {


//create post
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

//    update post
    PostDto updatePost(PostDto postDto, Integer postId);

//    delete post
    void deletePost(Integer postId);

//    get single post
    PostDto getPostById(Integer postId);

//    get all posts
    List<PostDto> getAllPosts();

    List<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);

    //    get all post by user
    List<PostDto> getAllPostByUser(Integer userId);

//    get all post by category
    List<PostDto> getAllPostsByCategory(Integer categoryId);

    //get posts by keyword
    List<PostDto> getPostByKeyword(String keyword);
}


