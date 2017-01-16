package com.epam.lab.news.manager.service.impl;

import com.epam.lab.news.manager.entity.User;
import com.epam.lab.news.manager.repository.UserRepository;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.service.UserService;
import com.epam.lab.news.manager.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Ivan_Lohvinau on 10/14/2016.
 */
public class CustomUserService implements UserService {
    private UserRepository userRepository;

    @Override
    public Long signUp(User user) throws ServiceException {
        Long result;

        try {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            result = userRepository.create(user);

        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User signIn(User user) throws ServiceException {
        User result = null;
        try {
            User registerUser = userRepository.findByLogin(user.getLogin());
            String hashPassword = DigestUtils.md5Hex(user.getPassword());
            if (registerUser != null && hashPassword.equals(registerUser.getPassword())) {
                result = registerUser;
            }
        } catch (RepositoryException e) {
            throw new ServiceException();
        }
        return result;
    }

    @Override
    public User findUserByLogin(String login) throws ServiceException {
        User result;
        try {
            result = userRepository.findByLogin(login);
        } catch (RepositoryException e) {
            throw new ServiceException();
        }
        return result;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
