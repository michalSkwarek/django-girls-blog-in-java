package com.skwarek.blog.service;

import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;

import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
public interface PostService {

    Post readPost(long postId);

    List<Post> findAllPublishedPosts();

    List<Post> findAllDrafts();

    void createPost(Post post);

    void updatePost(Post post);

    void publishPost(long postId);

    void addCommentToPost(Comment comment, long postId);

    void removePost(long postId);
}
