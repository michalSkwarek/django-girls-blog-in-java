package com.skwarek.blog.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skwarek.blog.MyEmbeddedDatabase;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;
import com.skwarek.blog.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Michal on 02/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestPostController {

    private final static long ZERO_HIBERNATE_ID = 0;

    private final static long FIRST_PUBLISHED_POST_ID = 1;
    private final static long SECOND_PUBLISHED_POST_ID = 2;
    private final static long DRAFT_POST_ID = 3;

    private final static long APPROVED_COMMENT_ID = 1;
    private final static long NOT_APPROVED_COMMENT_ID = 2;

    private final static Date CREATED_DATE = MyEmbeddedDatabase.getCreatedDate();
    private final static Date PUBLISHED_DATE = MyEmbeddedDatabase.getPublishedDate();

    private Post firstPublishedPost;
    private Post secondPublishedPost;
    private Post draftPost;
    private Post newPost;

    private Comment newComment;

    private PostService postService;

    private PostController postController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MyEmbeddedDatabase myDB = new MyEmbeddedDatabase();

        this.firstPublishedPost = myDB.getPost_no_1();
        this.secondPublishedPost = myDB.getPost_no_2();
        this.draftPost = myDB.getPost_no_3();
        this.newPost = new Post();
        this.newPost.setTitle("newTitle");
        this.newPost.setText("newText");

        this.newComment = new Comment();
        this.newComment.setAuthor("newAuthor");
        this.newComment.setText("newText");

        this.postService = mock(PostService.class);

        this.postController = new PostController(this.postService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.postController)
                .build();
    }

    @Test
    public void showPublishedPosts_ShouldAddPostEntriesToModelAndRenderPostListView() throws Exception {
        given(this.postService.findAllPublishedPosts()).willReturn(Arrays.asList(firstPublishedPost, secondPublishedPost));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is((int) FIRST_PUBLISHED_POST_ID)))
                .andExpect(jsonPath("$[0].title", is("title1")))
                .andExpect(jsonPath("$[0].text", is("text1")))
                .andExpect(jsonPath("$[0].createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$[0].publishedDate", is(PUBLISHED_DATE.getTime())))
                .andExpect(jsonPath("$[1].id", is((int) SECOND_PUBLISHED_POST_ID)))
                .andExpect(jsonPath("$[1].title", is("title2")))
                .andExpect(jsonPath("$[1].text", is("text2")))
                .andExpect(jsonPath("$[1].createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$[1].publishedDate", is(PUBLISHED_DATE.getTime())));

        verify(postService, times(1)).findAllPublishedPosts();
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void showDrafts_ShouldAddDraftEntriesToModelAndRenderDraftListView() throws Exception {
        given(this.postService.findAllDrafts()).willReturn(Collections.singletonList(draftPost));

        mockMvc.perform(get("/drafts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is((int) DRAFT_POST_ID)))
                .andExpect(jsonPath("$[0].title", is("title3")))
                .andExpect(jsonPath("$[0].text", is("text3")))
                .andExpect(jsonPath("$[0].createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$[0].publishedDate", nullValue()));

        verify(postService, times(1)).findAllDrafts();
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void showPost_PostEntryFound_ShouldAddPostEntryToModelAndRenderPostEntryView() throws Exception {
        given(this.postService.readPost(FIRST_PUBLISHED_POST_ID)).willReturn(firstPublishedPost);

        mockMvc.perform(get("/post/{postId}", FIRST_PUBLISHED_POST_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is((int) FIRST_PUBLISHED_POST_ID)))
                .andExpect(jsonPath("$.title", is("title1")))
                .andExpect(jsonPath("$.text", is("text1")))
                .andExpect(jsonPath("$.createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$.publishedDate", is(PUBLISHED_DATE.getTime())));

        verify(postService, times(1)).readPost(FIRST_PUBLISHED_POST_ID);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void showPost_PostAndCommentsEntryFound_ShouldAddCommentsEntryToModelAndRenderCommentsEntryView() throws Exception {
        given(this.postService.readPost(FIRST_PUBLISHED_POST_ID)).willReturn(firstPublishedPost);

        mockMvc.perform(get("/post/{postId}", FIRST_PUBLISHED_POST_ID))
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[0].id", is((int) APPROVED_COMMENT_ID)))
                .andExpect(jsonPath("$.comments[0].author", is("author1")))
                .andExpect(jsonPath("$.comments[0].text", is("commentText1")))
                .andExpect(jsonPath("$.comments[0].createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$.comments[0].approvedComment", is(true)))
                .andExpect(jsonPath("$.comments[1].id", is((int) NOT_APPROVED_COMMENT_ID)))
                .andExpect(jsonPath("$.comments[1].author", is("author2")))
                .andExpect(jsonPath("$.comments[1].text", is("commentText2")))
                .andExpect(jsonPath("$.comments[1].createdDate", is(CREATED_DATE.getTime())))
                .andExpect(jsonPath("$.comments[1].approvedComment", is(false)));
    }

    @Test
    public void processCreatePostForm_ShouldBeFine() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonNewPost = ow.writeValueAsString(newPost);

        mockMvc.perform(post("/post/new")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonNewPost)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("newTitle")))
                .andExpect(jsonPath("$.text", is("newText")));

        ArgumentCaptor<Post> formObjectArgument = ArgumentCaptor.forClass(Post.class);
        verify(postService, times(1)).createPost(formObjectArgument.capture());
        verifyNoMoreInteractions(postService);

        Post formPost = formObjectArgument.getValue();

        assertThat(formPost.getId(), is(ZERO_HIBERNATE_ID));
        assertThat(formPost.getAuthor(), nullValue());
        assertThat(formPost.getTitle(), is("newTitle"));
        assertThat(formPost.getText(), is("newText"));
        assertThat(formPost.getCreatedDate(), nullValue());
        assertThat(formPost.getPublishedDate(), nullValue());
    }

    @Test
    public void processEditPostForm_ShouldBeFine() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        firstPublishedPost.setTitle("editedTitle");
        firstPublishedPost.setText("editedText");
        String jsonEditedPost = ow.writeValueAsString(firstPublishedPost);

        mockMvc.perform(put("/post/{postId}/edit", FIRST_PUBLISHED_POST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonEditedPost)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is("editedTitle")))
                .andExpect(jsonPath("$.text", is("editedText")));

        ArgumentCaptor<Post> formObjectArgument = ArgumentCaptor.forClass(Post.class);
        verify(postService, times(1)).updatePost(formObjectArgument.capture());
        verifyNoMoreInteractions(postService);

        Post formPost = formObjectArgument.getValue();

        assertThat(formPost.getId(), is(FIRST_PUBLISHED_POST_ID));
        assertThat(formPost.getTitle(), is("editedTitle"));
        assertThat(formPost.getText(), is("editedText"));
        assertThat(formPost.getCreatedDate(), notNullValue());
        assertThat(formPost.getPublishedDate(), notNullValue());
    }

    @Test
    public void publishPost_ShouldAddPublishedDateToPost() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonDraftPost = ow.writeValueAsString(draftPost);

        mockMvc.perform(put("/post/{postId}/publish", DRAFT_POST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonDraftPost)
        )
                .andExpect(status().isOk());

        verify(postService, times(1)).publishPost(DRAFT_POST_ID);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void removePost_ShouldRemovePost() throws Exception {
        mockMvc.perform(delete("/post/{postId}/remove", FIRST_PUBLISHED_POST_ID))
                .andExpect(status().isOk());

        verify(postService, times(1)).removePost(FIRST_PUBLISHED_POST_ID);
        verifyNoMoreInteractions(postService);
    }

    @Test
    public void processCreateCommentForm_ShouldBeFine() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonNewComment = ow.writeValueAsString(newComment);

        mockMvc.perform(post("/post/{postId}/comment", FIRST_PUBLISHED_POST_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonNewComment)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.author", is("newAuthor")))
                .andExpect(jsonPath("$.text", is("newText")));

        ArgumentCaptor<Comment> formObjectArgument = ArgumentCaptor.forClass(Comment.class);
        verify(postService, times(1)).addCommentToPost(formObjectArgument.capture(), eq(FIRST_PUBLISHED_POST_ID));
        verifyNoMoreInteractions(postService);

        Comment formComment = formObjectArgument.getValue();

        assertThat(formComment.getId(), is(ZERO_HIBERNATE_ID));
        assertThat(formComment.getAuthor(), is("newAuthor"));
        assertThat(formComment.getText(), is("newText"));
        assertThat(formComment.getCreatedDate(), nullValue());
        assertThat(formComment.isApprovedComment(), is(false));
        assertThat(formComment.getPost(), nullValue());
    }
}