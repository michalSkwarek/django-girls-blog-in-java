package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.entity.Comment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Michal on 04/01/2017.
 */
public interface CommentDao extends CrudRepository<Comment, Long> {

}
