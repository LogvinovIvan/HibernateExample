package com.epam.lab.news.manager.repository;

import com.epam.lab.news.manager.entity.Tag;
import com.epam.lab.news.manager.exception.RepositoryException;

import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface TagRepository extends Repository<Tag,Long> {
    Set<Tag> findTagsForNews(Long idNews) throws RepositoryException;
}
