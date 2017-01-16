package com.epam.lab.news.manager.service.impl;

import com.epam.lab.news.manager.repository.AuthorRepository;
import com.epam.lab.news.manager.entity.Author;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.service.AuthorService;
import com.epam.lab.news.manager.exception.ServiceException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/14/2016.
 */

public class CustomAuthorService implements AuthorService {
    private AuthorRepository repository;

    @Override
    public Long addNewAuthor(Author author) throws ServiceException {
        Long insertId;
        try {
            insertId = repository.create(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return insertId;
    }

    @Override
    public boolean deleteAuthor(Long id) throws ServiceException {
        boolean result;
        try {
            result = repository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean updateAuthor(Author author) throws ServiceException {
        boolean result;
        try {
            result = repository.update(author);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Author findAuthor(Long id) throws ServiceException {
        Author author;
        try {
            author = repository.read(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return author;
    }

    @Override
    public List<Author> findAllAuthors() throws ServiceException {
        List<Author> authors;
        try {
            authors = repository.readAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return authors;
    }

    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }
}
