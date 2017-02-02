package com.skwarek.blog.domain.dao;

import com.skwarek.blog.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Michal on 05/01/2017.
 */
public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(@Param("username") String username);
}
