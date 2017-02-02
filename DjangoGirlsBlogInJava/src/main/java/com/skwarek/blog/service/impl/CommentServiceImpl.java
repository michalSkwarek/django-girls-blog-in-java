package com.skwarek.blog.service.impl;

import com.skwarek.blog.domain.dao.CommentDao;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Michal on 04/01/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void approve(long commentId) {
        Comment comment = commentDao.findOne(commentId);
        comment.setApprovedComment(true);
        commentDao.save(comment);
    }

    @Override
    public void removeComment(long commentId) {
        commentDao.delete(commentId);
    }
}
