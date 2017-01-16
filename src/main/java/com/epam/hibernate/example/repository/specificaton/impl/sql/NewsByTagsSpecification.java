package com.epam.hibernate.example.repository.specificaton.impl.sql;


import com.epam.hibernate.example.entity.Tag;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class NewsByTagsSpecification extends NewsSqlSpecification {


    private final static String SEARCH_BY_TAGS_QUERY = "SELECT\n" +
            "  N_ID,\n" +
            "  N_MAIN_TITLE,\n" +
            "  N_SHORT_TITLE,\n" +
            "  N_DATE_PUBLISHING,\n" +
            "  N_MAIN_PHOTO,\n" +
            "        ROW_NUMBER()\n" +
            "OVER (ORDER BY N_DATE_PUBLISHING DESC) AS rn\n "+
            "FROM NEWS_HAS_TAGS\n" +
            "  JOIN NEWS ON NEWS_HAS_TAGS.NEWS_ID = NEWS.N_ID\n" +
            "  JOIN ATTACHED_TAGS ON NEWS_HAS_TAGS.ATTACHED_TAGS_ID = ATTACHED_TAGS.T_ID\n" +
            "WHERE ATTACHED_TAGS.T_ID IN (%1$s)\n" +
            "  GROUP BY (N_ID, N_MAIN_TITLE, N_SHORT_TITLE, N_DATE_PUBLISHING, N_MAIN_PHOTO)\n" +
            "HAVING COUNT(DISTINCT ATTACHED_TAGS.T_NAME) >= ?"+
            "ORDER BY N_ID";
    private Set<Tag> tagList;

    public NewsByTagsSpecification(Set<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toSqlQuery() {
        StringBuilder stringForTags = new StringBuilder();
        for (int i = 1; i <= tagList.size(); i++) {
            stringForTags.append("?");
            if (i != tagList.size()) {
                stringForTags.append(",");
            }
        }

        return String.format(SEARCH_BY_TAGS_QUERY, stringForTags);
    }

    @Override
    public int fillPrepareStatement(PreparedStatement preparedStatement) throws RepositoryException {
        int numberParameters = 1;
        try {
            for (Tag tag : tagList) {
                preparedStatement.setLong(numberParameters, tag.getId());
                numberParameters++;
            }
            preparedStatement.setInt(numberParameters,tagList.size());
            numberParameters++;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return numberParameters;
    }

}
