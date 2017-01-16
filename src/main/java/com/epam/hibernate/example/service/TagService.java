package com.epam.lab.news.manager.service;

import com.epam.lab.news.manager.entity.Tag;
import com.epam.lab.news.manager.exception.ServiceException;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface TagService {
    Long add(Tag tag) throws ServiceException;
    boolean delete(Long idTag) throws ServiceException;
    boolean update(Tag tag) throws ServiceException;
    Tag search(Long id) throws ServiceException;
    List<Tag> searchAll() throws ServiceException;
}
