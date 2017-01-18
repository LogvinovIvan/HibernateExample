package com.epam.hibernate.example.service.impl;


import com.epam.hibernate.example.entity.Comment;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.exception.ServiceException;
import com.epam.hibernate.example.repository.CommentRepository;
import com.epam.hibernate.example.repository.impl.oracle.OracleCommentRepository;
import com.epam.hibernate.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/14/2016.
 */

@Transactional
public class CustomCommentService implements CommentService {

    @Autowired
    private CommentRepository repository;

    @Override
    public Long addComment(Comment comment) throws ServiceException {
        Long idComment;
        try {
            idComment = repository.create(comment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return idComment;
    }

    @Override
    public boolean editComment(Comment comment) throws ServiceException {
        boolean result;
        try {
            result = repository.update(comment);
        } catch (RepositoryException e) {
            throw  new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean deleteComment(Long idComment) throws ServiceException {
        boolean result;
        try {
            result = repository.delete(idComment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Comment findComment(Long idComment) throws ServiceException {
        Comment result;
        try {
            result = repository.read(idComment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException();
    }

    public void setRepository(CommentRepository commernRepository) {
        this.repository = commernRepository;
    }
}
