package com.epam.hibernate.example.repository;


import com.epam.hibernate.example.entity.Author;
import com.epam.hibernate.example.exception.RepositoryException;

import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface AuthorRepository extends Repository<Author,Long> {
    Set<Author> findByNewsID(Long newsID) throws RepositoryException;
}
