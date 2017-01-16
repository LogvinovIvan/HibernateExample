package com.epam.lab.news.manager.service.impl;

import com.epam.lab.news.manager.entity.User;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.repository.UserRepository;
import com.epam.lab.news.manager.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 11/4/2016.
 */
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            User user = userRepository.findByLogin(login);
            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(user.getRole().getName()));
            userDetails =
                    new org.springframework.security.core.userdetails.User(user.getLogin(),
                            user.getPassword(),
                            roles);

        } catch (RepositoryException e) {

            //e.printStackTrace();
        }
        return userDetails;
    }


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
