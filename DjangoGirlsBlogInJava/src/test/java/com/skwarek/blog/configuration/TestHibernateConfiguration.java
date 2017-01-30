package com.skwarek.blog.configuration;

import com.skwarek.blog.BlogSpringBootApplication;
import org.junit.Test;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.TestCase.assertNotNull;

@WebAppConfiguration
@SpringApplicationConfiguration(classes = BlogSpringBootApplication.class)
public class TestHibernateConfiguration extends AbstractJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testHibernateConfiguration() {
        assertNotNull (entityManager);
    }
}
