package com.skwarek.blog.domain.dao.impl;

import com.skwarek.blog.domain.dao.CommentDao;
import com.skwarek.blog.domain.dao.generic.GenericDaoImpl;
import com.skwarek.blog.domain.entity.Comment;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Michal on 04/01/2017.
 */
@Repository
public class CommentDaoImpl extends GenericDaoImpl<Comment, Long> implements CommentDao {

    @Override
    public boolean removeComment(long commentId) {
        Query removeComment = getSession().createQuery("delete from Comment c where c.id = :id");
        removeComment.setParameter("id", commentId);
        return removeComment.executeUpdate() > 0;
    }
}
