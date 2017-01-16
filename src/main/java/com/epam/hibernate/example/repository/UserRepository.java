package com.epam.lab.news.manager.repository;

import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.entity.User;

/**
 * Created by Ivan_Lohvinau on 10/13/2016.
 */
public interface UserRepository extends Repository<User,Long> {
    User findByLogin(String login) throws RepositoryException;
}
