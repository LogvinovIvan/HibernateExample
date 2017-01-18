package com.epam.hibernate.example.repository.impl.hibernate;

import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.exception.ServiceException;
import com.epam.hibernate.example.repository.TagRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 1/18/2017.
 */
@Repository
public class HibernateTagRepository implements TagRepository {

    @Autowired
    private SessionFactory sessionFactory;


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Set<Tag> findTagsForNews(Long idNews) throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long create(Tag tag) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Long) session.save(tag);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public Tag read(Long id) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Tag) session.get(Tag.class, id);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Tag> readAll() throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        try {
            return session.createCriteria(Tag.class).list();
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Tag transientObject) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        Session session = sessionFactory.getCurrentSession();
        try {
            Tag tag = new Tag();
            tag.setId(id);
            session.delete(tag);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
        return true;
    }
}
