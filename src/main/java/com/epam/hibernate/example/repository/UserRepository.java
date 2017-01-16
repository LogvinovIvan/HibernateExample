package com.epam.hibernate.example.repository;


import com.epam.hibernate.example.entity.User;
import com.epam.hibernate.example.exception.RepositoryException;

/**
 * Created by Ivan_Lohvinau on 10/13/2016.
 */
public interface UserRepository extends Repository<User,Long> {
    User findByLogin(String login) throws RepositoryException;
}
