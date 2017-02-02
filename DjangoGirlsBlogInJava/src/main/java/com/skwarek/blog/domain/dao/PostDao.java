package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.entity.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
public interface PostDao extends CrudRepository<Post, Long> {

    @Query("from Post p where p.publishedDate is not null order by p.publishedDate asc")
    List<Post> findAllPublishedPosts();

    @Query("from Post p where p.publishedDate is null order by p.createdDate asc")
    List<Post> findAllDrafts();
}
