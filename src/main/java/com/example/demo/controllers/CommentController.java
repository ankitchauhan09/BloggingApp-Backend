package com.example.demo.controllers;


import com.example.demo.entities.Comments;
import com.example.demo.entities.Post;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CommentDto;
import com.example.demo.repositories.CommentRepo;
import com.example.demo.repositories.PostRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;

@RestController
@RequestMapping("/api/post/{postId}")
@Tag(name = "Comment Controller" , description = "APIs for Handling Comments")
public class CommentController {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

//    POST - create post
    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@PathVariable("postId") Integer postId, @RequestBody CommentDto commentDto){
        try {
            Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
            Comments comment = new Comments();
            comment.setContent(commentDto.getContent());
            comment.setPost(post);
            Comments createdComment = this.commentRepo.save(comment);
            return new ResponseEntity<>(this.modelMapper.map(createdComment, CommentDto.class), HttpStatus.OK);
        }
        catch (Exception ignored){
            return new ResponseEntity<>(new ApiResponse("Post not found!!", false), HttpStatus.BAD_REQUEST);
        }
    }

//    PUT - update Comment
    @PutMapping("/comment/{commentId}")
    public CommentDto updateComment(@PathVariable("commendId") Integer commentId, @RequestBody CommentDto commentDto){
        {
            return null;
        }
    }


}

