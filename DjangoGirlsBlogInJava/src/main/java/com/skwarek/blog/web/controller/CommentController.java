package com.skwarek.blog.web.controller;

import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Michal on 04/01/2017.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "/{commentId}/approve", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void approveComment(@PathVariable long commentId) {
        Comment comment = commentService.read(commentId);
        commentService.approve(comment);
    }

    @RequestMapping(value = "/{commentId}/remove", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable long commentId) {
        commentService.removeComment(commentId);
    }
}
