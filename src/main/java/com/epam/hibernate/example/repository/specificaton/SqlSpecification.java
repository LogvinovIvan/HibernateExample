package com.epam.hibernate.example.repository.specificaton;


import com.epam.hibernate.example.exception.RepositoryException;

import java.sql.PreparedStatement;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public interface SqlSpecification {
    String toSqlQuery();
    int fillPrepareStatement(PreparedStatement preparedStatement) throws RepositoryException;
}
