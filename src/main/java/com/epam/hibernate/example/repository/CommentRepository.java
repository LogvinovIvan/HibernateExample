package com.epam.hibernate.example.repository;


import com.epam.hibernate.example.entity.Comment;
import com.epam.hibernate.example.exception.RepositoryException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface CommentRepository extends Repository<Comment,Long> {
    List findAllCommentsForNews(Long idNews) throws RepositoryException;
}
