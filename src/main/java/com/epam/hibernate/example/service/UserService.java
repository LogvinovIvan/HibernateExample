package com.epam.hibernate.example.service;


import com.epam.hibernate.example.entity.User;
import com.epam.hibernate.example.exception.ServiceException;

/**
 * Created by Ivan_Lohvinau on 10/12/2016.
 */
public interface UserService {
    Long signUp(User user) throws ServiceException;
    User signIn(User user) throws ServiceException;
    User findUserByLogin(String login) throws ServiceException;
}
