package com.epam.lab.news.manager.repository.impl;

import com.epam.lab.news.manager.entity.Role;
import com.epam.lab.news.manager.repository.UserRepository;
import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.entity.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class OracleUserRepository implements UserRepository {
    private static final String SELECT_USER_BY_LOGIN = "SELECT U_LOGIN, U_PASSWORD, U_ID, R_NAME FROM USERS JOIN ROLE ON USERS.U_ROLE = ROLE.R_ID WHERE U_LOGIN = ?";
    private static final String SELECT_USER_BY_ID = "SELECT U_LOGIN, U_PASSWORD, U_ID FROM USERS WHERE U_ID = ?";
    private static final String DELETE_USER = "DELETE FROM  USERS WHERE U_ID = ?";
    private static final String INSERT_USER_QUERY = "INSERT INTO USERS (U_LOGIN, U_PASSWORD, U_ROLE) VALUES(?,?,?)";
    private static final String UPDATE_USER_QUERY = "UPDATE USERS SET U_LOGIN = ?, U_PASSWORD = ? WHERE U_ID = ? ";

    private DataSource dataSource;

    @Override
    public User findByLogin(String login) throws RepositoryException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("U_ID"));
                user.setLogin(resultSet.getString("U_LOGIN"));
                user.setPassword(resultSet.getString("U_PASSWORD"));

                Role role = new Role();
                role.setName(resultSet.getString("R_NAME"));
                user.setRole(role);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return user;
    }


    @Override
    public Long create(User user) throws RepositoryException {
        Long idUser = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER_QUERY, new String[]{"U_ID"})
        ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getRole().getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                idUser = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return idUser;
    }

    //Fix null on Observable
    @Override
    public User read(Long id) throws RepositoryException {
        User user = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setLogin(rs.getString("U_LOGIN"));
                user.setPassword(rs.getString("U_PASSWORD"));
                user.setId(rs.getLong("U_ID"));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return user;
    }

    @Override
    public List<User> readAll() throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_QUERY)
        ) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)
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
}
