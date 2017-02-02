package com.skwarek.blog.service;

/**
 * Created by Michal on 04/01/2017.
 */
public interface CommentService {

    void approve(long commentId);

    void removeComment(long commentId);
}
