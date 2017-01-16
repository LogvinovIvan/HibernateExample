package com.epam.hibernate.example.repository.specificaton.impl.sql;


import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;

import java.sql.PreparedStatement;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class NewsOrderByCommentSpecificaion extends NewsSqlSpecification {



    private String SELECT_NEWS_ORDER_BY_COMMENTS = "SELECT DISTINCT    \n" +
            "                 N_ID,    \n" +
            "                 N_MAIN_TITLE,    \n" +
            "                 N_SHORT_TITLE,    \n" +
            "                 N_DATE_PUBLISHING,    \n" +
            "                 N_MAIN_PHOTO,    \n" +
            "                 COUNT(C_ID) AS COUNT_COMMENTS,    \n" +
            "                 ROW_NUMBER()    \n" +
            "                 OVER (    \n" +
            "                   ORDER BY COUNT(C_ID) DESC) AS rn    \n" +
            "               FROM COMMENT_NEWS    \n" +
            "                 RIGHT JOIN NEWS ON NEWS.N_ID = COMMENT_NEWS.C_NEWS    \n" +
            "               GROUP BY N_ID, N_MAIN_TITLE, N_SHORT_TITLE, N_DATE_PUBLISHING, N_MAIN_PHOTO";



    @Override
    public String toSqlQuery() {
        return SELECT_NEWS_ORDER_BY_COMMENTS;
    }

    @Override
    public int fillPrepareStatement(PreparedStatement preparedStatement) throws RepositoryException {
        return 1;
    }

}
