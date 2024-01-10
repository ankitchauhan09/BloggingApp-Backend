package com.example.demo.controllers;

import com.example.demo.entities.Post;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.PostDto;
import com.example.demo.services.FileService;
import com.example.demo.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jdk.javadoc.doclet.Reporter;
import org.apache.coyote.Response;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(name = "POST controller", description = "APIs for managing posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String fileUplaodPath;


    //    POST - create Post
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<?> crPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId) {
        try {
            PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("create post", false), HttpStatus.BAD_REQUEST);
        }
    }

    //    update post
    @PutMapping("/post/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Integer postId, @RequestBody PostDto postDto) {
        try {
            PostDto updatedPost = this.postService.updatePost(postDto, postId);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("post updated", false), HttpStatus.BAD_REQUEST);
        }
    }

    //    delete post
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Integer postId) {
        try {
            this.postService.deletePost(postId);
            return new ResponseEntity<>(new ApiResponse("post deleted", true), HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("post deleted", false), HttpStatus.BAD_REQUEST);
        }
    }

    //get posts
//    1. Post by PostId
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") Integer postId) {
        try {
            PostDto post = this.postService.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception ignored) {
        }
        return new ResponseEntity<>(new ApiResponse("found post", false), HttpStatus.BAD_REQUEST);
    }

    //    2. Post by user
    @GetMapping("/user/{userId}/post")
    public ResponseEntity<?> getPostByUserId(@PathVariable("userId") Integer userId) {
        try {
            List<PostDto> post = this.postService.getAllPostByUser(userId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("found post", false), HttpStatus.BAD_REQUEST);
        }
    }

    //    3. Post by Category
    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<?> getPostByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        try {
            List<PostDto> post = this.postService.getAllPostsByCategory(categoryId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("found post", false), HttpStatus.BAD_REQUEST);
        }
    }

    //4. Post by Keyword
    @GetMapping("/post/title/{keyword}")
    public ResponseEntity<?> getPostByKeyword(@PathVariable("keyword") String keyword) {
        try {
            List<PostDto> post = this.postService.getPostByKeyword(keyword);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("Post found", false), HttpStatus.BAD_REQUEST);
        }
    }

    //5. Get all posts
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "50", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy
    ) {
        try {
            List<PostDto> post = this.postService.getAllPosts(pageNumber, pageSize, sortBy);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("found post", false), HttpStatus.BAD_REQUEST);
        }
    }

    //    post image upload
    @PostMapping("/post/{postId}/image")
    public ResponseEntity<?> uploadPostImage(@PathVariable("postId") Integer postId, @RequestParam("image") MultipartFile file) {
        try {
            PostDto currentPost = this.postService.getPostById(postId);
            currentPost.setImageName(this.fileService.uploadImage(fileUplaodPath, file));

            PostDto updatedPost = this.postService.updatePost(currentPost, postId);
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("Post not found!!", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/post/{postId}/image")
    public void getPostImage(@PathVariable("postId") Integer postId, HttpServletResponse response) {
        try {
            PostDto currentPost = this.postService.getPostById(postId);
            String imageName = currentPost.getImageName();
            String redirectUrl = "http://localhost:9090/api/post/"+postId+"/image/"+imageName;
            response.sendRedirect(redirectUrl);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    @GetMapping(value = "/post/{postId}/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("postId") Integer postId,@PathVariable("imageName") String imageName,HttpServletResponse response) {
        try{
            InputStream resource = this.fileService.getResource(fileUplaodPath, imageName);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());

        }
        catch (Exception ignored){
            ignored.printStackTrace();
        }
    }


}
