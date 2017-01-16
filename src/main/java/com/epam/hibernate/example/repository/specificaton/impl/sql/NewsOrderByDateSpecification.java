package com.epam.hibernate.example.repository.specificaton.impl.sql;


import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.specificaton.NewsSqlSpecification;

import java.sql.PreparedStatement;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class NewsOrderByDateSpecification extends NewsSqlSpecification {


    private final static  String SELECT_ORDER_BY_DATE =
            "SELECT DISTINCT\n" +
            "        N_ID,\n" +
            "        N_MAIN_TITLE,\n" +
            "        N_SHORT_TITLE,\n" +
            "        N_DATE_PUBLISHING,\n" +
            "        N_MAIN_PHOTO,\n" +
            "        ROW_NUMBER()\n" +
            "        OVER (\n" +
            "          ORDER BY N_DATE_PUBLISHING DESC) AS rn\n" +
            "      FROM NEWS\n" +
            "      ORDER BY N_DATE_PUBLISHING DESC";
    @Override
    public String toSqlQuery() {
        return SELECT_ORDER_BY_DATE;
    }

    @Override
    public int fillPrepareStatement(PreparedStatement preparedStatement) throws RepositoryException {
       return 1;
    }


}
