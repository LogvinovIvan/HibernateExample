package com.epam.hibernate.example.service.impl;


import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.exception.ServiceException;
import com.epam.hibernate.example.repository.Repository;
import com.epam.hibernate.example.service.TagService;

import java.util.List;

public class CustomTagService implements TagService {
    private Repository<Tag, Long> repository;

    @Override
    public Long add(Tag tag) throws ServiceException {
        Long idTag;
        try {
            idTag = repository.create(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return idTag;
    }

    @Override
    public boolean delete(Long idTag) throws ServiceException {
        boolean result;
        try {
            result = repository.delete(idTag);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Tag tag) throws ServiceException {
        boolean result;
        try {
            result = repository.update(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Tag search(Long id) throws ServiceException {
        Tag tag;
        try {
            tag = repository.read(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public List<Tag> searchAll() throws ServiceException {
        List<Tag> tags;
        try {
            tags = repository.readAll();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return tags;
    }



    public void setRepository(Repository<Tag, Long> repository) {
        this.repository = repository;
    }
}
