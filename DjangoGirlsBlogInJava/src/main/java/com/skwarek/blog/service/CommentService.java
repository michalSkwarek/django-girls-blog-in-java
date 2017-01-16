package com.skwarek.blog.service;

import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.service.generic.GenericService;

/**
 * Created by Michal on 04/01/2017.
 */
public interface CommentService extends GenericService<Comment, Long> {

    void approve(long commentId);

    boolean removeComment(long commentId);
}
