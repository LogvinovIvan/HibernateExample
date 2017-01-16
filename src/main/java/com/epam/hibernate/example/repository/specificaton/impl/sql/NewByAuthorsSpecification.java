package com.epam.hibernate.example.repository.specificaton.impl.sql;


import com.epam.hibernate.example.entity.Author;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 11/28/2016.
 */
public class NewByAuthorsSpecification extends NewsSqlSpecification {
    private String SEARCH_BY_AUTHORS_QUERY = "SELECT\n" +
            "  N_ID,\n" +
            "  N_MAIN_TITLE,\n" +
            "  N_SHORT_TITLE,\n" +
            "  N_DATE_PUBLISHING,\n" +
            "    N_MAIN_PHOTO,\n" +
            "        ROW_NUMBER()\n" +
            "OVER (ORDER BY N_DATE_PUBLISHING DESC) AS rn\n"+
            "FROM NEWS_HAS_AUTHORS\n" +
            "  JOIN NEWS ON NEWS_HAS_AUTHORS.NEWS_ID = NEWS.N_ID\n" +
            "  JOIN AUTHORS ON NEWS_HAS_AUTHORS.AUTHORS_ID = AUTHORS.A_ID\n" +
            "WHERE AUTHORS.A_ID IN (%1$s)\n" +
            "GROUP BY (N_ID, N_MAIN_TITLE, N_SHORT_TITLE, N_DATE_PUBLISHING, N_MAIN_PHOTO)\n" +
            "HAVING COUNT(DISTINCT AUTHORS.A_ID) >= ?\n" +
            "ORDER BY N_ID";

    private Set<Author> authors;

    public NewByAuthorsSpecification(Set<Author> authors){
        this.authors = authors;
    }

    @Override
    public String toSqlQuery() {
        StringBuilder stringForAuthors = new StringBuilder();
        for (int i = 1; i <= authors.size(); i++) {
            stringForAuthors.append("?");
            if (i != authors.size()) {
                stringForAuthors.append(",");
            }
        }
        return  String.format(SEARCH_BY_AUTHORS_QUERY, stringForAuthors);
    }

    @Override
    public int fillPrepareStatement(PreparedStatement preparedStatement) throws RepositoryException {
        int numberParameters = 1;
        try {
            for (Author author : authors) {
                preparedStatement.setLong(numberParameters, author.getId());
                numberParameters++;
            }
            preparedStatement.setInt(numberParameters,authors.size());
            numberParameters++;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return numberParameters;
    }

}
