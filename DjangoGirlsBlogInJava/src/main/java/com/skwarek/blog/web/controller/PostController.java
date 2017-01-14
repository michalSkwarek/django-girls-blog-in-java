package com.skwarek.blog.web.controller;

import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;
import com.skwarek.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Michal on 02/01/2017.
 */
@RestController
public class PostController {

    private final static String VIEWS_POST_LIST = "blog/post_list";
    private final static String VIEWS_DRAFTS = "blog/post_draft_list";
    private final static String VIEWS_POST_DETAIL = "blog/post_detail";
    private final static String VIEWS_POST_FORM = "blog/post_edit";
    private final static String VIEWS_COMMENT_FORM = "blog/add_comment_to_post";

    private final static String REDIRECT_TO = "redirect:";
    private final static String HOME_PAGE = "/";
    private final static String POST_PAGE = "/post";

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

    @RequestMapping(value = "/post/new", method = RequestMethod.GET)
    public String initCreatePostForm(Model model) {

        model.addAttribute("post", new Post());
        return VIEWS_POST_FORM;
    }

    @RequestMapping(value = "/post/new", method = RequestMethod.POST)
    public String processCreatePostForm(@Valid Post post, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return VIEWS_POST_FORM;
        }

        postService.createPost(post);
        return REDIRECT_TO + HOME_PAGE;
    }

    @RequestMapping(value = "/post/{postId}/edit", method = RequestMethod.GET)
    public String initEditPostForm(Model model, @PathVariable long postId) {

        Post post = postService.read(postId);
        model.addAttribute("post", post);
        return VIEWS_POST_FORM;
    }

    @RequestMapping(value = "/post/{postId}/edit", method = RequestMethod.POST)
    public String processEditPostForm(@Valid Post post, BindingResult bindingResult, @PathVariable long postId) {

        if (bindingResult.hasErrors()) {
            return VIEWS_POST_FORM;
        }

        postService.updatePost(post);
        return REDIRECT_TO + HOME_PAGE;
    }

    @RequestMapping(value = "post/{postId}/publish", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void publishPost(@PathVariable long postId) {
        Post post = postService.read(postId);
        postService.publishPost(post);
    }

    @RequestMapping(value = "/post/{postId}/remove", method = RequestMethod.DELETE)
    public String removePost(@PathVariable long postId) {

        postService.removePost(postId);
        return REDIRECT_TO + HOME_PAGE;
    }

    @RequestMapping(value = "/post/{postId}/comment", method = RequestMethod.GET)
    public String initCreateCommentForm(Model model, @PathVariable long postId) {

        model.addAttribute("comment", new Comment());
        return VIEWS_COMMENT_FORM;
    }

    @RequestMapping(value = "/post/{postId}/comment", method = RequestMethod.POST)
    public String processCreateCommentForm(@Valid Comment comment, BindingResult bindingResult, @PathVariable long postId) {

        if (bindingResult.hasErrors()) {
            return VIEWS_COMMENT_FORM;
        }

        postService.addCommentToPost(comment, postId);
        return REDIRECT_TO + POST_PAGE + "/" + postId;
    }
}
