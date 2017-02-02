package com.skwarek.blog.domain.entity;

import com.skwarek.blog.MyEmbeddedDatabase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Michal on 05/01/2017.
 */
public class TestPost extends AbstractJavaBeanTest<Post> {

    private Post post;

    @Before
    public void setUp() {
        MyEmbeddedDatabase myDB = new MyEmbeddedDatabase();

        this.post = myDB.getPost_no_1();
    }

    @Override
    protected Post getBeanInstance() {
        return post;
    }

    @Test
    public void toStringCorrectness() throws Exception {
        assertEquals("Post(title=title1)", post.toString());
    }
}