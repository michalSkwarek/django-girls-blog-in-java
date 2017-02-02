package com.skwarek.blog.service;

import com.skwarek.blog.BlogSpringBootApplication;
import com.skwarek.blog.MyEmbeddedDatabase;
import com.skwarek.blog.domain.dao.CommentDao;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.service.impl.CommentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Created by Michal on 05/01/2017.
 */
@SpringApplicationConfiguration(classes = BlogSpringBootApplication.class)
public class TestCommentService {

    private final static long APPROVED_COMMENT_ID = 1;
    private final static long NOT_APPROVED_COMMENT_ID = 2;

    private Comment approvedComment;
    private Comment notApprovedComment;

    private CommentDao commentDao;

    @Autowired
    private CommentService commentService;

    @Before
    public void setUp() {
        MyEmbeddedDatabase myDB = new MyEmbeddedDatabase();

        this.approvedComment = myDB.getComment_no_1();
        this.notApprovedComment = myDB.getComment_no_2();

        this.commentDao = mock(CommentDao.class);

        this.commentService = new CommentServiceImpl(commentDao);
    }

    @Test
    public void testApproveComment() throws Exception {
        given(commentDao.findOne(NOT_APPROVED_COMMENT_ID)).willReturn(notApprovedComment);

        commentService.approve(NOT_APPROVED_COMMENT_ID);

        assertEquals(true, notApprovedComment.isApprovedComment());

        verify(commentDao, times(1)).findOne(NOT_APPROVED_COMMENT_ID);
        verify(commentDao, times(1)).save(notApprovedComment);
        verifyNoMoreInteractions(commentDao);
    }

    @Test
    public void testRemoveComment() throws Exception {
        doNothing().when(this.commentDao).delete(APPROVED_COMMENT_ID);

        commentService.removeComment(APPROVED_COMMENT_ID);

        verify(commentDao, times(1)).delete(APPROVED_COMMENT_ID);
        verifyNoMoreInteractions(commentDao);
    }
}
