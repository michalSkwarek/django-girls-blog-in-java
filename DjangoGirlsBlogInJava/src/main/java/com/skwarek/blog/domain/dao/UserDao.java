package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.dao.generic.GenericDao;
import com.skwarek.blog.domain.entity.User;

/**
 * Created by Michal on 05/01/2017.
 */
public interface UserDao extends GenericDao<User, Long> {

    User findUserByUsername(String username);
}
