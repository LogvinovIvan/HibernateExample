package com.epam.hibernate.example.repository.impl.hibernate;

import com.epam.hibernate.example.entity.Author;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.AuthorRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by Иван on 16.01.2017.
 */
@Repository
public class HibernateAuthorRepository implements AuthorRepository {

    @Autowired
    private SessionFactory factory;

    @Autowired
    public HibernateAuthorRepository(SessionFactory sessionFactory){
        this.factory = sessionFactory;
    }

    @Override
    public Long create(Author newInstance) throws RepositoryException {
        return null;
    }

    @Override
    public Author read(Long id) throws RepositoryException {
        return null;
    }

    @Override
    public List<Author> readAll() throws RepositoryException {
        return null;
    }

    @Override
    public boolean update(Author transientObject) throws RepositoryException {
        return false;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        return false;
    }

    @Override
    public Set<Author> findByNewsID(Long newsID) throws RepositoryException {
        return null;
    }
}
