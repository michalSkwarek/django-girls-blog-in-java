package com.skwarek.blog.web.controller;

import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;
import com.skwarek.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> showPublishedPosts() {
        return postService.findAllPublishedPosts();
    }

    @RequestMapping(value = "/drafts", method = RequestMethod.GET)
    public List<Post> showDrafts() {
        return postService.findAllDrafts();
    }

    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    public Post showPost(@PathVariable long postId) {
        return postService.read(postId);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public void processCreatePostForm(@RequestBody Post post) {
        postService.createPost(post);
    }

    @RequestMapping(value = "/post/{postId}/edit", method = RequestMethod.PUT)
    public void processEditPostForm(@RequestBody Post post, @PathVariable long postId) {
        postService.updatePost(post);
    }

    @RequestMapping(value = "/post/{postId}/publish", method = RequestMethod.PUT)
    public void publishPost(@PathVariable long postId) {
        postService.publishPost(postId);
    }

    @RequestMapping(value = "/post/{postId}/remove", method = RequestMethod.DELETE)
    public void removePost(@PathVariable long postId) {
        postService.removePost(postId);
    }

    @RequestMapping(value = "/post/{postId}/comment", method = RequestMethod.POST)
    public void processCreateCommentForm(@RequestBody Comment comment, @PathVariable long postId) {
        postService.addCommentToPost(comment, postId);
    }
}
