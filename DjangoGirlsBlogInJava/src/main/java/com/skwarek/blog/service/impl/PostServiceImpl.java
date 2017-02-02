package com.skwarek.blog.service.impl;

import com.skwarek.blog.domain.dao.CommentDao;
import com.skwarek.blog.domain.dao.PostDao;
import com.skwarek.blog.domain.dao.UserDao;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;
import com.skwarek.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostServiceImpl implements PostService {

    private final PostDao postDao;
    private final CommentDao commentDao;
    private final UserDao userDao;

    @Autowired
    public PostServiceImpl(PostDao postDao, CommentDao commentDao, UserDao userDao) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Post readPost(long postId) {
        return postDao.findOne(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllPublishedPosts() {
        return postDao.findAllPublishedPosts();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAllDrafts() {
        return postDao.findAllDrafts();
    }

    @Override
    public void createPost(Post post) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setAuthor(userDao.findByUsername(auth.getName()));
        post.setCreatedDate(new Date());
        postDao.save(post);
    }

    @Override
    public void updatePost(Post post) {
        Post oldPost = postDao.findOne(post.getId());
        oldPost.setTitle(post.getTitle());
        oldPost.setText(post.getText());
        postDao.save(oldPost);
    }

    @Override
    public void publishPost(long postId) {
        Post post = postDao.findOne(postId);
        post.setPublishedDate(new Date());
        postDao.save(post);
    }

    @Override
    public void addCommentToPost(Comment comment, long postId) {
        comment.setCreatedDate(new Date());
        comment.setApprovedComment(false);
        comment.setPost(postDao.findOne(postId));
        commentDao.save(comment);
    }

    @Override
    public void removePost(long postId) {
        postDao.delete(postId);
    }
}
