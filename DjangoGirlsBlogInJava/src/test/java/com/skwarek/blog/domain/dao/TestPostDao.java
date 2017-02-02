package com.skwarek.blog.domain.dao;

import com.skwarek.blog.BlogSpringBootApplication;
import com.skwarek.blog.domain.entity.Comment;
import com.skwarek.blog.domain.entity.Post;
import com.skwarek.blog.domain.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Michal on 02/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlogSpringBootApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@WebAppConfiguration
@Transactional
public class TestPostDao {

    private final static long FIRST_AUTHOR_ID = 1;
    private final static long SECOND_AUTHOR_ID = 2;

    private final static long FIRST_PUBLISHED_POST_ID = 1;
    private final static long SECOND_PUBLISHED_POST_ID = 2;
    private final static long DRAFT_POST_ID = 3;

    private final static long APPROVED_COMMENT_ID = 1;
    private final static long NOT_APPROVED_COMMENT_ID = 2;

    private final static Date CREATED_DATE = new GregorianCalendar(2000, Calendar.JANUARY, 11, 11, 22, 33).getTime();
    private final static Date PUBLISHED_DATE = new GregorianCalendar(2000, Calendar.JANUARY, 12, 22, 33, 44).getTime();

    private User firstAuthor;
    private User secondAuthor;

    private Post newPost;

    private Comment newComment;

    private Comment approvedComment;
    private Comment notApprovedComment;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @Before
    public void setUp() {
        this.firstAuthor = userDao.findOne(FIRST_AUTHOR_ID);
        this.secondAuthor = userDao.findOne(SECOND_AUTHOR_ID);

        this.newPost = new Post();
        this.newPost.setAuthor(firstAuthor);
        this.newPost.setTitle("newTitle");
        this.newPost.setText("newText");
        this.newPost.setCreatedDate(CREATED_DATE);

        this.newComment = new Comment();
        this.newComment.setAuthor("newAuthor");
        this.newComment.setText("newText");

        this.approvedComment = commentDao.findOne(APPROVED_COMMENT_ID);
        this.notApprovedComment = commentDao.findOne(NOT_APPROVED_COMMENT_ID);
    }

    @Test
    public void testReadPost() throws Exception {
        Post found = postDao.findOne(FIRST_PUBLISHED_POST_ID);

        assertNotNull(found);
        assertEquals(FIRST_PUBLISHED_POST_ID, found.getId());
        assertEquals(firstAuthor, found.getAuthor());
        assertEquals("title1", found.getTitle());
        assertEquals("text1", found.getText());
        assertEquals(CREATED_DATE, found.getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.getPublishedDate());
        assertEquals(Arrays.asList(approvedComment, notApprovedComment), found.getComments());
    }

    @Test
    public void testCreatePost() throws Exception {
        assertEquals(3, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(2, commentDao.count());

        postDao.save(newPost);

        long newPostId = 4;
        Post found = postDao.findOne(newPostId);

        assertNotNull(found);
        assertEquals(newPostId, found.getId());
        assertEquals(firstAuthor, found.getAuthor());
        assertEquals("newTitle", found.getTitle());
        assertEquals("newText", found.getText());
        assertEquals(CREATED_DATE, found.getCreatedDate());
        assertEquals(null, found.getPublishedDate());
        assertEquals(Collections.emptyList(), found.getComments());

        assertEquals(4, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(2, commentDao.count());
    }

    @Test
    public void testUpdatePost() throws Exception {
        assertEquals(3, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(2, commentDao.count());

        Post toUpdate = postDao.findOne(FIRST_PUBLISHED_POST_ID);
        toUpdate.setTitle("editedTitle");
        toUpdate.setText("editedText");
        postDao.save(toUpdate);

        Post found = postDao.findOne(FIRST_PUBLISHED_POST_ID);

        assertNotNull(found);
        assertEquals(FIRST_PUBLISHED_POST_ID, found.getId());
        assertEquals(firstAuthor, found.getAuthor());
        assertEquals("editedTitle", found.getTitle());
        assertEquals("editedText", found.getText());
        assertEquals(CREATED_DATE, found.getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.getPublishedDate());
        assertEquals(Arrays.asList(approvedComment, notApprovedComment), found.getComments());

        assertEquals(3, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(2, commentDao.count());
    }

    @Test
    public void testFindAll() throws Exception {
        List<Post> found = (List<Post>) postDao.findAll();

        assertEquals(2 + 1, found.size());

        assertEquals(FIRST_PUBLISHED_POST_ID, found.get(0).getId());
        assertEquals(firstAuthor, found.get(0).getAuthor());
        assertEquals("title1", found.get(0).getTitle());
        assertEquals("text1", found.get(0).getText());
        assertEquals(CREATED_DATE, found.get(0).getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.get(0).getPublishedDate());
        assertEquals(Arrays.asList(approvedComment, notApprovedComment), found.get(0).getComments());

        assertEquals(SECOND_PUBLISHED_POST_ID, found.get(1).getId());
        assertEquals(secondAuthor, found.get(1).getAuthor());
        assertEquals("title2", found.get(1).getTitle());
        assertEquals("text2", found.get(1).getText());
        assertEquals(CREATED_DATE, found.get(1).getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.get(1).getPublishedDate());
        assertEquals(Collections.emptyList(), found.get(1).getComments());

        assertEquals(DRAFT_POST_ID, found.get(2).getId());
        assertEquals(firstAuthor, found.get(2).getAuthor());
        assertEquals("title3", found.get(2).getTitle());
        assertEquals("text3", found.get(2).getText());
        assertEquals(CREATED_DATE, found.get(2).getCreatedDate());
        assertEquals(null, found.get(2).getPublishedDate());
        assertEquals(Collections.emptyList(), found.get(2).getComments());
    }

    @Test
    public void testFindAllPublishedPosts() throws Exception {
        List<Post> found = postDao.findAllPublishedPosts();

        assertEquals(2, found.size());

        assertEquals(FIRST_PUBLISHED_POST_ID, found.get(0).getId());
        assertEquals(firstAuthor, found.get(0).getAuthor());
        assertEquals("title1", found.get(0).getTitle());
        assertEquals("text1", found.get(0).getText());
        assertEquals(CREATED_DATE, found.get(0).getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.get(0).getPublishedDate());
        assertEquals(Arrays.asList(approvedComment, notApprovedComment), found.get(0).getComments());

        assertEquals(SECOND_PUBLISHED_POST_ID, found.get(1).getId());
        assertEquals(secondAuthor, found.get(1).getAuthor());
        assertEquals("title2", found.get(1).getTitle());
        assertEquals("text2", found.get(1).getText());
        assertEquals(CREATED_DATE, found.get(1).getCreatedDate());
        assertEquals(PUBLISHED_DATE, found.get(1).getPublishedDate());
        assertEquals(Collections.emptyList(), found.get(1).getComments());
    }

    @Test
    public void testFindAllDrafts() throws Exception {
        List<Post> found = postDao.findAllDrafts();

        assertEquals(1, found.size());

        assertEquals(DRAFT_POST_ID, found.get(0).getId());
        assertEquals(firstAuthor, found.get(0).getAuthor());
        assertEquals("title3", found.get(0).getTitle());
        assertEquals("text3", found.get(0).getText());
        assertEquals(CREATED_DATE, found.get(0).getCreatedDate());
        assertEquals(null, found.get(0).getPublishedDate());
        assertEquals(Collections.emptyList(), found.get(0).getComments());
    }

    @Test
    public void testRemovePost() throws Exception {
        assertEquals(3, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(2, commentDao.count());

        postDao.delete(FIRST_PUBLISHED_POST_ID);

        assertEquals(2, postDao.count());
        assertEquals(2, userDao.count());
        assertEquals(0, commentDao.count());
    }
}
