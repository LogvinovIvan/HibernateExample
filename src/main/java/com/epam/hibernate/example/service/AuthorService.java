package com.epam.lab.news.manager.service;

import com.epam.lab.news.manager.entity.Author;
import com.epam.lab.news.manager.exception.ServiceException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface AuthorService {
    Long addNewAuthor(Author author) throws ServiceException;
    boolean deleteAuthor(Long id) throws ServiceException;
    boolean updateAuthor(Author author) throws ServiceException;
    Author findAuthor(Long id) throws ServiceException;
    List<Author> findAllAuthors() throws ServiceException;
}
