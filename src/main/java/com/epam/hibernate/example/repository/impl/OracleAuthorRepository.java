package com.epam.lab.news.manager.repository.impl;

import com.epam.lab.news.manager.repository.AuthorRepository;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.entity.Author;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivan_Lohvinau on 10/14/2016.
 */

public class OracleAuthorRepository implements AuthorRepository {
    private DataSource dataSource;
    private final static String SELECT_ALL_AUTHORS_QUERY = "SELECT A_ID, A_NAME, A_SURNAME FROM AUTHORS WHERE A_STATUS = 'Y'";
    private final static String SELECT_AUTHOR_BY_ID = "SELECT A_ID, A_NAME, A_SURNAME FROM AUTHORS WHERE A_STATUS = 'Y' AND A_ID = ?";
    private final static String CHANGE_NOT_ACTIVE_STATUS = "UPDATE AUTHORS SET A_STATUS = 'N' WHERE A_ID = ?";
    private final static String UPDATE_AUTHOR_QUERY = "UPDATE AUTHORS SET A_NAME = ?, A_SURNAME = ? WHERE A_ID = ?";
    private final static String INSERT_AUTHOR_QUERY = "INSERT INTO AUTHORS (A_NAME, A_SURNAME, A_STATUS) VALUES (?,?,'Y')";
    private final static String SELECT_AUTHORS_BY_NEWS_ID_QUERY = "SELECT\n" +
            "  A_NAME,\n" +
            "  A_SURNAME,\n" +
            "  A_ID\n" +
            "FROM NEWS_HAS_AUTHORS\n" +
            "  JOIN AUTHORS ON AUTHORS.A_ID = NEWS_HAS_AUTHORS.AUTHORS_ID\n" +
            "  JOIN NEWS ON NEWS_HAS_AUTHORS.NEWS_ID = NEWS.N_ID\n" +
            "WHERE NEWS_ID = ?";
    @Override
    public Long create(Author author) throws RepositoryException {
        Long idAuthor = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR_QUERY,new String[]{"A_ID"})
        ) {
            statement.setString(1,author.getName() );
            statement.setString(2, author.getSurname());
            statement.executeUpdate();
            ResultSet resultSet  = statement.getGeneratedKeys();
            if(resultSet.next()){
                idAuthor = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return idAuthor;
    }

    @Override
    public Author read(Long id) throws RepositoryException {
        Author author = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                author = new Author();
                author.setId(resultSet.getLong("A_ID"));
                author.setSurname(resultSet.getString("A_SURNAME"));
                author.setName(resultSet.getString("A_NAME"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return author;
    }

    @Override
    public List<Author> readAll() throws RepositoryException {
        List<Author> authors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_AUTHORS_QUERY)
        ) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Author author = new Author();
                author.setName(rs.getString("A_NAME"));
                author.setSurname(rs.getString("A_SURNAME"));
                author.setId(rs.getLong("A_ID"));
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return authors;
    }

    @Override
    public boolean update(Author author) throws RepositoryException {
        boolean result;
        try {
            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR_QUERY)
            ) {
                statement.setString(1,author.getName());
                statement.setString(2, author.getSurname());
                statement.setLong(3, author.getId());
                result = statement.executeUpdate()!=0;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHANGE_NOT_ACTIVE_STATUS)
        ) {
            statement.setLong(1,id);
            result = statement.executeUpdate()!=0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<Author> findByNewsID(Long newsID) throws RepositoryException {
        Set<Author> authors = new HashSet<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_AUTHORS_BY_NEWS_ID_QUERY)
        ) {
            statement.setLong(1,newsID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Author author = new Author();
                author.setId(rs.getLong("A_ID"));
                author.setName(rs.getString("A_NAME"));
                author.setSurname(rs.getString("A_SURNAME"));
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return authors;
    }
}
