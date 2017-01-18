package com.epam.hibernate.example.service.impl;



import com.epam.hibernate.example.entity.Author;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.exception.ServiceException;
import com.epam.hibernate.example.repository.AuthorRepository;
import com.epam.hibernate.example.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;




public class CustomAuthorService implements AuthorService {
    @Autowired
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

    @Autowired
    public void setRepository(AuthorRepository repository) {
        this.repository = repository;
    }
}
