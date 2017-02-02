package com.skwarek.blog.domain.dao.generic;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Michal on 26.09.2016.
 */
@Repository
public abstract class GenericDaoImpl<E, PK extends Serializable> implements GenericDao<E, PK> {

    private Class<E> daoType;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public GenericDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        daoType = (Class<E>) parameterizedType.getActualTypeArguments()[0];
    }

    protected Session getSession()  {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public void create(E entity) {
        getSession().save(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E read(PK id) {
        return (E) getSession().get(daoType, id);
    }

    @Override
    public void update(E entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(E entity) {
        getSession().delete(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return getSession().createCriteria(daoType).list();
    }
}

