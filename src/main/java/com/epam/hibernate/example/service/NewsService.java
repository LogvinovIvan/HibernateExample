package com.epam.lab.news.manager.service;


import com.epam.lab.news.manager.entity.News;
import com.epam.lab.news.manager.entity.SearchCriteria;
import com.epam.lab.news.manager.entity.Tag;
import com.epam.lab.news.manager.exception.ServiceException;
import com.epam.lab.news.manager.repository.specificaton.NewsSpecification;
import com.epam.lab.news.manager.repository.specificaton.NewsSqlSpecification;

import java.util.List;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface NewsService {
    void add(News news) throws ServiceException;
    boolean attachTag(Long idNews, Long idTag) throws ServiceException;
    boolean addAuthorToNews(Long idNews, Long idAuthor) throws ServiceException;
    boolean removeTagFromNews(Long idNews, Long idTag) throws ServiceException;
    boolean removeAuthorFromNews(Long idNews, Long idAuthor) throws ServiceException;
    void deleteNews(Long id) throws ServiceException;
    void updateNews(News news) throws ServiceException;
    List<News> searchByTags(Set<Tag> tags, Integer offset, Integer count) throws ServiceException;
    List<News> findNewsSortedByDate(Integer offset, Integer count) throws ServiceException;
    List<News> findNewsSortedByCountComments(Integer offset, Integer count) throws ServiceException;
    List<News> findNews(SearchCriteria criteria, Integer offset, Integer count) throws ServiceException;
    News searchSingleNews(Long id) throws ServiceException;
    Integer searchTotalCountNews(SearchCriteria criteria) throws ServiceException;
    Integer searchCountNewsByTheme(Tag tag) throws ServiceException;
    Integer searchTotalCountNews() throws ServiceException;
    boolean deleteNews(List<Long> idList) throws ServiceException;

}
