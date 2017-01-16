package com.epam.hibernate.example.repository;


import com.epam.hibernate.example.entity.News;
import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.specificaton.NewsSpecification;

import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/13/2016.
 */
public interface NewsRepository extends Repository<News, Long> {
    List<News> search(NewsSpecification sqlSpecification, int offset, int count) throws RepositoryException;
    Integer searchTotalCountNews(NewsSpecification specification) throws RepositoryException;
    Integer searchCountNewsForTheme(Tag tag) throws RepositoryException;
    Integer searchTotalCountNews() throws RepositoryException;
    boolean attachTagToNews(Long tagId, Long newsId) throws RepositoryException;
    boolean addAuthor(Long authorId, Long newsId) throws RepositoryException;
    boolean removeAuthor(Long idNews, Long idAuthor) throws RepositoryException;
    boolean removeTag(Long idNews, Long idTag) throws RepositoryException;
}
