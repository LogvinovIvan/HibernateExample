package com.epam.hibernate.example.repository.impl.hibernate;

import com.epam.hibernate.example.entity.Comment;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.CommentRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 1/17/2017.
 */
@Repository
@Transactional
public class HibernateCommentRepository implements CommentRepository {

    private String SELECT_COMMENT_BY_NEWS_ID = "from COMMENT_NEWS as comments join comments.user as user where comments.C_NEWS = :id";

    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public List<Comment> findAllCommentsForNews(Long idNews) throws RepositoryException {
        return factory.getCurrentSession().
                       createQuery(SELECT_COMMENT_BY_NEWS_ID).
                       setParameter("id",idNews).list();
    }

    @Override
    public Long create(Comment comment) throws RepositoryException {
        Session session = factory.getCurrentSession();
        try {
            return (Long) session.save(comment);
        }catch (HibernateException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public Comment read(Long id) throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Comment> readAll() throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Comment comment) throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        try {
            Session session = factory.getCurrentSession();
            Comment comment = new Comment();
            comment.setId(id);
            session.delete(comment);
        }catch (HibernateException e){
            throw new RepositoryException("Error delete comment with id = " + id,e);
        }
        return true;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }
}
