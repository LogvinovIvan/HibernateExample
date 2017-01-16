package com.epam.lab.news.manager.service;

import com.epam.lab.news.manager.entity.FullNews;
import com.epam.lab.news.manager.entity.SearchCriteria;
import com.epam.lab.news.manager.entity.Tag;
import com.epam.lab.news.manager.exception.ServiceException;
import com.epam.lab.news.manager.repository.specificaton.NewsSpecification;
import com.epam.lab.news.manager.repository.specificaton.NewsSqlSpecification;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface FullNewsService {
    Long add(FullNews fullNews) throws ServiceException;

    FullNews findFullNews(Long newsId) throws ServiceException;

    List<FullNews> findFullNewsSortedByComment(Integer offset, Integer count) throws ServiceException;

    List<FullNews> findFullNewsSortedByDate(Integer offset, Integer count) throws ServiceException;

    List<FullNews> findFullNews(SearchCriteria criteria, Integer offset, Integer count) throws ServiceException;

    boolean updateFullNews(FullNews fullNews) throws ServiceException;

    List<FullNews> searchByTags(Set<Tag> tags, Integer offset, Integer count) throws ServiceException;
}
