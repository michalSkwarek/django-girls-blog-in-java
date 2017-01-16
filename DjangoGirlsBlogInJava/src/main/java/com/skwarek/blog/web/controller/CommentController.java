package com.skwarek.blog.web.controller;

import com.skwarek.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public void approveComment(@PathVariable long commentId) {
        commentService.approve(commentId);
    }

    @RequestMapping(value = "/{commentId}/remove", method = RequestMethod.DELETE)
    public void removeComment(@PathVariable long commentId) {
        commentService.removeComment(commentId);
    }
}
