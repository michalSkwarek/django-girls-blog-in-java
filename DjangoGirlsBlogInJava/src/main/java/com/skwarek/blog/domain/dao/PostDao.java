package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.dao.generic.GenericDao;
import com.skwarek.blog.domain.entity.Post;

import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
public interface PostDao extends GenericDao<Post, Long> {

    List findAllPublishedPosts();

    List findAllDrafts();

    boolean removePost(long postId);
}
