package com.skwarek.blog.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.skwarek.blog.MyEmbeddedDatabase;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.service.CommentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Michal on 05/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestCommentController {

    private final static long APPROVED_COMMENT_ID = 1;
    private final static long NOT_APPROVED_COMMENT_ID = 2;

    private Comment approvedComment;
    private Comment notApprovedComment;

    private CommentService commentService;
    private CommentController commentController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MyEmbeddedDatabase myDB = new MyEmbeddedDatabase();

        this.approvedComment = myDB.getComment_no_1();
        this.notApprovedComment = myDB.getComment_no_2();

        this.commentService = mock(CommentService.class);

        this.commentController = new CommentController(this.commentService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.commentController)
                .build();
    }

    @Test
    public void approveComment_ShouldSetApproveFlagToComment() throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonNotApprovedComment = ow.writeValueAsString(notApprovedComment);

        mockMvc.perform(put("/comment/{commentId}/approve", NOT_APPROVED_COMMENT_ID)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonNotApprovedComment)
        )
                .andExpect(status().isOk());

        verify(commentService, times(1)).approve(NOT_APPROVED_COMMENT_ID);
        verifyNoMoreInteractions(commentService);
    }

    @Test
    public void removeComment_ShouldRemoveComment() throws Exception {
        mockMvc.perform(delete("/comment/{commentId}/remove", APPROVED_COMMENT_ID))
                .andExpect(status().isOk());

        verify(commentService, times(1)).removeComment(APPROVED_COMMENT_ID);
        verifyNoMoreInteractions(commentService);
    }
}
