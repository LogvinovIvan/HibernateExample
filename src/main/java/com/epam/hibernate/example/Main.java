package com.epam.hibernate.example;

import com.epam.hibernate.example.config.ApplicationConfig;
import com.epam.hibernate.example.entity.Comment;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.CommentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        CommentRepository repository = context.getBean(CommentRepository.class);
        try {
            List<Comment> comments  = repository.findAllCommentsForNews(66l);
        } catch (RepositoryException e) {
            System.out.print(e);
        }
    }
}
