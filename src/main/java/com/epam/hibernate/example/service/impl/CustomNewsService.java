package com.epam.hibernate.example.service.impl;


import com.epam.hibernate.example.entity.News;
import com.epam.hibernate.example.entity.SearchCriteria;
import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.exception.ServiceException;
import com.epam.hibernate.example.repository.NewsRepository;
import com.epam.hibernate.example.repository.specificaton.NewsSpecification;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;
import com.epam.hibernate.example.repository.specificaton.factory.NewsSpecificationFactory;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsByTagsSpecification;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsOrderByCommentSpecificaion;
import com.epam.hibernate.example.repository.specificaton.impl.sql.NewsOrderByDateSpecification;
import com.epam.hibernate.example.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public class CustomNewsService implements NewsService {
    private NewsRepository repository;
    private NewsSpecificationFactory factory;

    @Override
    public void add(News news) throws ServiceException {
        try {
            repository.create(news);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean attachTag(Long idNews, Long idTag) throws ServiceException {
        boolean result;
        try {
            result = repository.attachTagToNews(idTag, idNews);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean addAuthorToNews(Long idNews, Long idAuthor) throws ServiceException {
        boolean result;
        try {
            result = repository.addAuthor(idNews, idAuthor);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean removeTagFromNews(Long idNews, Long idTag) throws ServiceException {
        boolean result;
        try {
            result = repository.removeTag(idNews, idTag);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }

        return result;
    }

    @Override
    public boolean removeAuthorFromNews(Long idNews, Long idAuthor) throws ServiceException {
        boolean result;
        try {
            result = repository.removeAuthor(idNews,idAuthor);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public void deleteNews(Long id) throws ServiceException {
        try {
            repository.delete(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateNews(News news) throws ServiceException {
        try {
            repository.update(news);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<News> searchByTags(Set<Tag> tags, Integer offset, Integer count) throws ServiceException {
        List<News> news;
        NewsSqlSpecification sqlSpecification = new NewsByTagsSpecification(tags);
        try {
            news = repository.search(sqlSpecification, offset, count);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> findNewsSortedByDate(Integer offset, Integer count) throws ServiceException {
        List<News> news;
        NewsSqlSpecification sqlSpecification = new NewsOrderByDateSpecification();

        try {
            news = repository.search(sqlSpecification, offset, count);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> findNewsSortedByCountComments(Integer offset, Integer count) throws ServiceException {
        List<News> news;
        NewsSqlSpecification sqlSpecification = new NewsOrderByCommentSpecificaion();
        try {
            news = repository.search(sqlSpecification, offset, count);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> findNews(SearchCriteria criteria, Integer offset, Integer count) throws ServiceException {
        List<News> news;
        try {
            NewsSpecification specification = factory.createSpecification(criteria);
            news = repository.search(specification, offset, count);
        } catch (RepositoryException e) {
            throw  new ServiceException(e);
        }
        return news;
    }


    @Override
    public News searchSingleNews(Long id) throws ServiceException {
        News news;
        try {
            news = repository.read(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public Integer searchTotalCountNews(SearchCriteria criteria) throws ServiceException {
        Integer result;
        try {
            NewsSpecification specification = factory.createSpecification(criteria);
            result = repository.searchTotalCountNews(specification);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Integer searchCountNewsByTheme(Tag tag) throws ServiceException {
        Integer result;
        try {
            result = repository.searchCountNewsForTheme(tag);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Integer searchTotalCountNews() throws ServiceException {
        try {
            return repository.searchTotalCountNews();
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    //Think
    @Override
    public boolean deleteNews(List<Long> idList) throws ServiceException {
        boolean result = true;
        try {
            for (Long id : idList) {
                if (!repository.delete(id)) {
                    result = false;
                    break;
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    public void setRepository(NewsRepository repository) {
        this.repository = repository;
    }

    public NewsSpecificationFactory getFactory() {
        return factory;
    }

    public void setFactory(NewsSpecificationFactory factory) {
        this.factory = factory;
    }
}
