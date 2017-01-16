package com.epam.lab.news.manager.service.impl;

import com.epam.lab.news.manager.entity.Comment;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.repository.impl.OracleCommentRepository;
import com.epam.lab.news.manager.service.CommentService;
import com.epam.lab.news.manager.exception.ServiceException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/14/2016.
 */
public class CustomCommentService implements CommentService {
    private OracleCommentRepository oracleCommentRepository;

    @Override
    public Long addComment(Comment comment) throws ServiceException {
        Long idComment;
        try {
            idComment = oracleCommentRepository.create(comment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return idComment;
    }

    @Override
    public boolean editComment(Comment comment) throws ServiceException {
        boolean result;
        try {
            result = oracleCommentRepository.update(comment);
        } catch (RepositoryException e) {
            throw  new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean deleteComment(Long idComment) throws ServiceException {
        boolean result;
        try {
            result = oracleCommentRepository.delete(idComment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Comment findComment(Long idComment) throws ServiceException {
        Comment result;
        try {
            result = oracleCommentRepository.read(idComment);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException();
    }

    public void setOracleCommentRepository(OracleCommentRepository commernRepository) {
        this.oracleCommentRepository = commernRepository;
    }
}
