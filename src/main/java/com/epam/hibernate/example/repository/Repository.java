package com.epam.hibernate.example.repository;



import com.epam.hibernate.example.exception.RepositoryException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/13/2016.
 */
public interface Repository<T, PK extends Serializable> {
    PK create(T newInstance) throws RepositoryException;
    T read(PK id) throws RepositoryException;
    List<T> readAll() throws RepositoryException;
    boolean update(T transientObject) throws RepositoryException;
    boolean delete(PK id) throws RepositoryException;
}
