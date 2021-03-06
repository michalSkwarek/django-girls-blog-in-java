package com.skwarek.blog.domain.dao;

import com.skwarek.blog.BlogSpringBootApplication;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Michal on 05/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlogSpringBootApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@WebAppConfiguration
@Transactional
public class TestUserDao {

    private static final long FIRST_PUBLISHED_POST_ID = 1;
    private static final long SECOND_PUBLISHED_POST_ID = 2;
    private static final long DRAFT_POST_ID = 3;

    private static final long FIRST_AUTHOR_ID = 1;

    private Post firstPublishedPost;
    private Post secondPublishedPost;
    private Post draftPost;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Before
    public void setUp() {
        this.firstPublishedPost = postDao.findOne(FIRST_PUBLISHED_POST_ID);
        this.secondPublishedPost = postDao.findOne(SECOND_PUBLISHED_POST_ID);
        this.draftPost = postDao.findOne(DRAFT_POST_ID);
    }

    @Test
    public void testReadUser() throws Exception {
        User found = userDao.findOne(FIRST_AUTHOR_ID);

        assertNotNull(found);
        assertEquals(FIRST_AUTHOR_ID, found.getId());
        assertEquals("user1", found.getUsername());
        assertEquals("pass1", found.getPassword());
        assertEquals(true, found.getEnabled());
        assertEquals("ADMIN", found.getRole());
        assertEquals(Arrays.asList(firstPublishedPost, draftPost), found.getPosts());
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> found = (List<User>) userDao.findAll();

        assertEquals(2, found.size());

        assertEquals(1, found.get(0).getId());
        assertEquals("user1", found.get(0).getUsername());
        assertEquals("pass1", found.get(0).getPassword());
        assertEquals(true, found.get(0).getEnabled());
        assertEquals("ADMIN", found.get(0).getRole());
        assertEquals(Arrays.asList(firstPublishedPost, draftPost), found.get(0).getPosts());

        assertEquals(2, found.get(1).getId());
        assertEquals("user2", found.get(1).getUsername());
        assertEquals("pass2", found.get(1).getPassword());
        assertEquals(true, found.get(1).getEnabled());
        assertEquals("ADMIN", found.get(1).getRole());
        assertEquals(Collections.singletonList(secondPublishedPost), found.get(1).getPosts());
    }

    @Test
    public void testFindUserByUsername() {
        User notFound = userDao.findByUsername("notFound");

        assertNull(notFound);

        User found = userDao.findByUsername("user1");

        assertNotNull(found);
        assertEquals(FIRST_AUTHOR_ID, found.getId());
        assertEquals("user1", found.getUsername());
        assertEquals("pass1", found.getPassword());
        assertEquals(true, found.getEnabled());
        assertEquals("ADMIN", found.getRole());
        assertEquals(Arrays.asList(firstPublishedPost, draftPost), found.getPosts());
    }
}
