package com.skwarek.blog.domain.dao.impl;

import com.skwarek.blog.domain.dao.UserDao;
import com.skwarek.blog.domain.dao.generic.GenericDaoImpl;
import com.skwarek.blog.domain.entity.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Michal on 05/01/2017.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @Override
    public User findUserByUsername(String username) {
        Query getUserQuery = getSession().createQuery("from User u where u.username = :username");
        getUserQuery.setParameter("username", username);
        getUserQuery.setMaxResults(1);
        return (User) getUserQuery.uniqueResult();
    }
}
