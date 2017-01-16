package com.epam.lab.news.manager.service.impl;

import com.epam.lab.news.manager.entity.*;
import com.epam.lab.news.manager.repository.AuthorRepository;
import com.epam.lab.news.manager.repository.CommentRepository;
import com.epam.lab.news.manager.repository.NewsRepository;
import com.epam.lab.news.manager.repository.TagRepository;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.repository.impl.OracleAuthorRepository;
import com.epam.lab.news.manager.repository.impl.OracleCommentRepository;
import com.epam.lab.news.manager.repository.impl.OracleTagRepository;
import com.epam.lab.news.manager.repository.specificaton.NewsSpecification;
import com.epam.lab.news.manager.repository.specificaton.NewsSqlSpecification;
import com.epam.lab.news.manager.service.FullNewsService;
import com.epam.lab.news.manager.exception.ServiceException;
import com.epam.lab.news.manager.service.NewsService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class CustomFullNewsService implements FullNewsService {
    private AuthorRepository authorBaseRepository;
    private NewsRepository newsBaseRepository;
    private CommentRepository commentBaseRepository;
    private TagRepository tagBaseRepository;

    private NewsService service;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long add(FullNews fullNews) throws ServiceException {
        Long idNews;
        try {
            idNews = newsBaseRepository.create(fullNews.getNews());
            if(idNews!=null){
                for(Tag tag: fullNews.getTags()){
                    newsBaseRepository.attachTagToNews(tag.getId(),idNews);
                }

                for (Author author : fullNews.getAuthors()){
                    newsBaseRepository.addAuthor(author.getId(),idNews);
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return idNews;
    }

    @Override
    public FullNews findFullNews(Long newsId) throws ServiceException {
        FullNews fullNews = null;
        try {
            News news = newsBaseRepository.read(newsId);
            if(news!=null){
                fullNews = new FullNews();
                fullNews.setNews(news);
                Set<Tag> tags = tagBaseRepository.findTagsForNews(newsId);
                fullNews.setTags(tags);
                Set<Author> authors = authorBaseRepository.findByNewsID(newsId);
                fullNews.setAuthors(authors);
                List<Comment> comments = commentBaseRepository.findAllCommentsForNews(newsId);
                fullNews.setComments(comments);
            }

        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return fullNews;
    }

    @Override
    public List<FullNews> findFullNewsSortedByComment(Integer offset, Integer count) throws ServiceException {
        List<News> newsList = service.findNewsSortedByCountComments(offset,count);
        return formFullNewsList(newsList);
    }

    @Override
    public List<FullNews> findFullNewsSortedByDate(Integer offset, Integer count) throws ServiceException {
        List<News> newsList = service.findNewsSortedByDate(offset,count);
        return formFullNewsList(newsList);
    }

    @Override
    public List<FullNews> findFullNews(SearchCriteria criteria, Integer offset, Integer count) throws ServiceException {
        List<News> newsList = service.findNews(criteria, offset, count);
        return formFullNewsList(newsList);
    }

    @Override
    public boolean updateFullNews(FullNews fullNews) throws ServiceException {

        try {
            newsBaseRepository.update(fullNews.getNews());
            replaceTagForNews(fullNews);
            replaceAuthorsForNews(fullNews);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return true;
    }

    @Override
    public List<FullNews> searchByTags(Set<Tag> tags, Integer offset, Integer count) throws ServiceException {
        List<News> newsList = service.searchByTags(tags, offset,count);
        return formFullNewsList(newsList);
    }

    private List<FullNews> formFullNewsList(List<News> newsList) throws ServiceException {
        List<FullNews> fullNewsList = new ArrayList<>();
        try {
            for(News news:newsList){
                FullNews fullNews = new FullNews();
                fullNews.setNews(news);
                Set<Tag> tags = tagBaseRepository.findTagsForNews(news.getId());
                fullNews.setTags(tags);
                Set<Author> authors = authorBaseRepository.findByNewsID(news.getId());
                fullNews.setAuthors(authors);
                List<Comment> comments = commentBaseRepository.findAllCommentsForNews(news.getId());
                fullNews.setComments(comments);
                fullNewsList.add(fullNews);
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return fullNewsList;
    }






    private void replaceTagForNews(FullNews fullNews) throws ServiceException {
        try {
            Set<Tag> oldTags = tagBaseRepository.findTagsForNews(fullNews.getNews().getId());
            Set<Tag> matchingTags = new HashSet<>(fullNews.getTags());
            matchingTags.retainAll(oldTags);

            oldTags.removeAll(matchingTags);
            fullNews.getTags().removeAll(matchingTags);

            Set<Long> deletedTagsId = oldTags.stream().map(Tag::getId).collect(Collectors.toSet());
            Set<Long> insertedTagId = fullNews.getTags().stream().map(Tag::getId).collect(Collectors.toSet());

            for(Long id:deletedTagsId){
                newsBaseRepository.removeTag(fullNews.getNews().getId(),id);
            }

            for(Long id: insertedTagId){
                newsBaseRepository.attachTagToNews(id,fullNews.getNews().getId());
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }

    }



    private void replaceAuthorsForNews(FullNews fullNews) throws ServiceException {
        try {
            Set<Author> oldAuthors = authorBaseRepository.findByNewsID(fullNews.getNews().getId());
            Set<Author> matchingAuthors = new HashSet<>(fullNews.getAuthors());
            matchingAuthors.retainAll(oldAuthors);

            oldAuthors.removeAll(matchingAuthors);
            fullNews.getAuthors().removeAll(matchingAuthors);

            Set<Long> deletedAuthorsId = oldAuthors.stream().map(Author::getId).collect(Collectors.toSet());
            Set<Long> insertedAuthorsId = fullNews.getAuthors().stream().map(Author::getId).collect(Collectors.toSet());

            for(Long id:deletedAuthorsId){
                newsBaseRepository.removeAuthor(fullNews.getNews().getId(),id);
            }

            for(Long id: insertedAuthorsId){
                newsBaseRepository.addAuthor(id,fullNews.getNews().getId());
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void setAuthorBaseRepository(OracleAuthorRepository authorBaseRepository) {
        this.authorBaseRepository = authorBaseRepository;
    }

    public void setNewsBaseRepository(NewsRepository newsBaseRepository) {
        this.newsBaseRepository = newsBaseRepository;
    }

    public void setCommentBaseRepository(OracleCommentRepository commentBaseRepository) {
        this.commentBaseRepository = commentBaseRepository;
    }

    public void setTagBaseRepository(OracleTagRepository tagBaseRepository) {
        this.tagBaseRepository = tagBaseRepository;
    }

    public void setService(NewsService service) {
        this.service = service;
    }
}
