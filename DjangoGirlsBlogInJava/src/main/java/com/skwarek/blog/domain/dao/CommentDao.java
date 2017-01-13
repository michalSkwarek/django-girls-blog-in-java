package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.dao.generic.GenericDao;
import com.skwarek.blog.domain.entity.Comment;

/**
 * Created by Michal on 04/01/2017.
 */
public interface CommentDao extends GenericDao<Comment, Long> {

    boolean removeComment(long commentId);
}
