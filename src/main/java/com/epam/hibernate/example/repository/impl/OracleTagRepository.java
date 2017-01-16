package com.epam.lab.news.manager.repository.impl;

import com.epam.lab.news.manager.repository.TagRepository;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.entity.Tag;

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
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class OracleTagRepository implements TagRepository {
    private DataSource dataSource;
    private static final String INSERT_TAG_QUERY = "INSERT INTO ATTACHED_TAGS (T_NAME) VALUES(?)";
    private static final String SELECT_TAG_BY_ID_QUERY = "SELECT T_NAME FROM ATTACHED_TAGS WHERE T_ID = ?";
    private static final String UPDATE_TAG_QUERY = "UPDATE ATTACHED_TAGS SET T_NAME = ? WHERE T_ID = ?";
    private static final String DELETE_TAG_QUERY = "DELETE FROM ATTACHED_TAGS WHERE T_ID = ?";
    private static final String SELECT_TAG_BY_NEWS_ID_QUERY = "\n" +
            "SELECT\n" +
            "  T_NAME,\n" +
            "  T_ID\n" +
            "FROM NEWS_HAS_TAGS\n" +
            "  JOIN ATTACHED_TAGS ON NEWS_HAS_TAGS.ATTACHED_TAGS_ID = ATTACHED_TAGS.T_ID\n" +
            "  JOIN NEWS ON NEWS_HAS_TAGS.NEWS_ID = NEWS.N_ID\n" +
            "WHERE NEWS_ID = ?";
    private static final String SELECT_ALL_TAG_QUERY = "SELECT T_ID, T_NAME FROM ATTACHED_TAGS";

    @Override
    public Long create(Tag tag) throws RepositoryException {
        Long idTag = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TAG_QUERY, new String[]{"T_ID"})
        ) {
            statement.setString(1, tag.getName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                idTag = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return idTag;
    }

    @Override
    public Tag read(Long id) throws RepositoryException {
        Tag tag = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TAG_BY_ID_QUERY)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = new Tag();
                tag.setId(id);
                tag.setName(resultSet.getString("T_NAME"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return tag;
    }

    @Override
    public List<Tag> readAll() throws RepositoryException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TAG_QUERY)
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Tag tag = new Tag();
                tag.setName(resultSet.getString("T_NAME"));
                tag.setId(resultSet.getLong("T_ID"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Error update tag", e);
        }
        return tags;
    }

    @Override
    public boolean update(Tag tag) throws RepositoryException {
        boolean result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TAG_QUERY)
        ) {
            statement.setLong(2, tag.getId());
            statement.setString(1, tag.getName());
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RepositoryException("Error update tag", e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_TAG_QUERY)
        ) {
            statement.setLong(1, id);
            result = statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Set<Tag> findTagsForNews(Long idNews) throws RepositoryException {
        Set<Tag> tags = new HashSet<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TAG_BY_NEWS_ID_QUERY)
        ) {
            statement.setLong(1, idNews);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setId(rs.getLong("T_ID"));
                tag.setName(rs.getString("T_NAME"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return tags;
    }
}
