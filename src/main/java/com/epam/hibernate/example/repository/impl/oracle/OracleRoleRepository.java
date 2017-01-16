package com.epam.hibernate.example.repository.impl.oracle;




import com.epam.hibernate.example.entity.Role;
import com.epam.hibernate.example.exception.RepositoryException;
import com.epam.hibernate.example.repository.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class OracleRoleRepository implements Repository<Role,Long> {
    private final static String INSERT_NEW_ROLE_QUERY = "INSERT INTO ROLE (R_NAME) VALUES (?)";
    private final static String SELECT_ROLE_BY_ID = "SELECT R_NAME FROM ROLE WHERE R_ID = ?";
    private final static String DELETE_ROLE_BY_ID = "DELETE FROM ROLE WHERE R_ID = ?";
    private final static String UPDATE_ROLE_QUERY = "UPDATE ROLE SET R_NAME = ? WHERE R_ID = ?";

    private DataSource dataSource;
    @Override
    public Long create(Role role) throws RepositoryException {
        Long id = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_ROLE_QUERY,new String[]{"R_ID"})
        ) {
            statement.setString(1, role.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return id;
    }

    @Override
    public Role read(Long id) throws RepositoryException {
        Role role = new Role();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ID)
        ){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                role.setName(resultSet.getString("R_NAME"));
                role.setId(id);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return role;
    }

    @Override
    public List<Role> readAll() throws RepositoryException {
        return null;
    }

    @Override
    public boolean update(Role role) throws RepositoryException {
        boolean result;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE_QUERY)
        ) {
            statement.setString(1, role.getName());
            statement.setLong(2, role.getId());
            result =  statement.executeUpdate()!=0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_BY_ID);
        ) {
            preparedStatement.setLong(1,id);
            result = preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
