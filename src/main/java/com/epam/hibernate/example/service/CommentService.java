package com.epam.lab.news.manager.service;

import com.epam.lab.news.manager.entity.Comment;
import com.epam.lab.news.manager.exception.ServiceException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface CommentService {
    Long addComment(Comment comment) throws ServiceException;
    boolean editComment(Comment comment) throws ServiceException;
    boolean deleteComment(Long idComment) throws ServiceException;
    Comment findComment(Long idComment) throws ServiceException;
    List<Comment> findAll();
}
