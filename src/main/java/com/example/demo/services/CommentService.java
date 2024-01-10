package com.example.demo.services;

import com.example.demo.entities.Comments;
import com.example.demo.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);

    void delete(Integer commentId);





}
