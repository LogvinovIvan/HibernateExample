package com.epam.hibernate.example.repository;


import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;

import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface TagRepository extends Repository<Tag,Long> {
    Set<Tag> findTagsForNews(Long idNews) throws RepositoryException;
}
