package com.epam.hibernate.example.repository.impl.hibernate;

import com.epam.hibernate.example.entity.Author;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.AuthorRepository;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Иван on 16.01.2017.
 */
@Repository
public class HibernateAuthorRepository implements AuthorRepository {

    private final static String CHANGE_NOT_ACTIVE_STATUS = "UPDATE AUTHORS SET A_STATUS = 'N' WHERE A_ID = :author_id";

    @Autowired
    private SessionFactory factory;

    @Autowired
    public HibernateAuthorRepository(SessionFactory sessionFactory){
        this.factory = sessionFactory;
    }

    @Override
    public Long create(Author author) throws RepositoryException {
        Session session = factory.getCurrentSession();
        try {
            return (Long) session.save(author);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public Author read(Long id) throws RepositoryException {
        Session session = factory.getCurrentSession();
        try {
            return (Author) session.get(Author.class, id);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Author> readAll() throws RepositoryException {
        Session session = factory.getCurrentSession();
        try {
            return  session.createCriteria(Author.class).list();
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean update(Author author) throws RepositoryException {
        Session session = factory.getCurrentSession();
        try {
            session.saveOrUpdate(author);
            return true;
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        Session session = factory.getCurrentSession();
        try {
            SQLQuery query = session.createSQLQuery(CHANGE_NOT_ACTIVE_STATUS);
            query.setParameter("author_id",id);
            result = query.executeUpdate()!=0;
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public Set<Author> findByNewsID(Long newsID) throws RepositoryException {
       throw new UnsupportedOperationException();

    }
}
