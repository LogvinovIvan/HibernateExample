package com.epam.lab.news.manager.service;

import com.epam.lab.news.manager.entity.User;
import com.epam.lab.news.manager.exception.ServiceException;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface UserService {
    Long signUp(User user) throws ServiceException;
    User signIn(User user) throws ServiceException;
    User findUserByLogin(String login) throws ServiceException;
}
