package com.epam.lab.news.manager.repository.impl;

import com.epam.lab.news.manager.exception.RepositoryException;
import com.epam.lab.news.manager.entity.Comment;
import com.epam.lab.news.manager.entity.User;
import com.epam.lab.news.manager.repository.CommentRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan_Lohvinau on 10/17/2016.
 */
public class OracleCommentRepository implements CommentRepository {
    private final static String SELECT_COMMENT_BY_ID = "SELECT C_NEWS,USER_ID_USER,C_DATE,C_CONTENT FROM COMMENT_NEWS WHERE C_ID =?";
    private final static String SELECT_ALL_COMMENT_BY_NEWS = "SELECT\n" +
            "  C_ID,\n" +
            "  USER_ID_USER,\n" +
            "  C_DATE,\n" +
            "  C_CONTENT,\n" +
            "  U_ID,\n" +
            "  U_LOGIN\n" +
            "FROM COMMENT_NEWS JOIN USERS ON COMMENT_NEWS.USER_ID_USER = USERS.U_ID\n" +
            "WHERE C_NEWS = ?";
    private final static String DELETE_COMMENT_QUERY = "DELETE FROM COMMENT_NEWS WHERE C_ID = ?";
    private final static String INSERT_COMMENT_QUERY = "INSERT INTO COMMENT_NEWS (USER_ID_USER, C_DATE,C_CONTENT,C_NEWS) VALUES (?,?,?,?)";
    private final static String UPDATE_COMMENT_QUERY = "UPDATE COMMENT_NEWS SET C_DATE = ?, C_CONTENT = ? WHERE C_ID = ?";


    private DataSource dataSource;
    @Override
    public Long create(Comment comment) throws RepositoryException {
        Long idComment = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENT_QUERY,new String[]{"C_ID"})
        ) {
            preparedStatement.setLong(1,comment.getUser().getId());
            preparedStatement.setDate(2,comment.getDate());
            preparedStatement.setString(3, comment.getText());
            preparedStatement.setLong(4,comment.getIdNews());
            preparedStatement.executeUpdate();

            //GET generated key

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                idComment = rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return idComment;
    }

    @Override
    public Comment read(Long id) throws RepositoryException {
        Comment comment = null;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENT_BY_ID)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                comment = new Comment();
                comment.setId(id);
                comment.setText(resultSet.getString("C_CONTENT"));
                comment.setIdNews(resultSet.getLong("C_NEWS"));
                comment.setDate(resultSet.getDate("C_DATE"));
                User user = new User();
                user.setId(resultSet.getLong("USER_ID_USER"));
                comment.setUser(user);
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return comment;
    }

    @Override
    public List<Comment> readAll() throws RepositoryException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Comment comment) throws RepositoryException {
        boolean result;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENT_QUERY)
        ) {
            statement.setDate(1,comment.getDate());
            statement.setString(2,comment.getText());
            statement.setLong(3, comment.getId());

            result = statement.executeUpdate()!=0;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Long id) throws RepositoryException {
        boolean result;
        try(Connection connection  = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT_QUERY)
        ) {
            statement.setLong(1,id);
            int changedColumns = statement.executeUpdate();
            result = changedColumns==1;
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return result;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Comment> findAllCommentsForNews(Long idNews) throws RepositoryException {
        List<Comment> comments = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_COMMENT_BY_NEWS)
        ) {
            statement.setLong(1,idNews);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Comment comment = new Comment();
                comment.setIdNews(idNews);
                comment.setId(rs.getLong("C_ID"));
                comment.setText(rs.getString("C_CONTENT"));
                comment.setDate(rs.getDate("C_DATE"));
                User user = new User();
                user.setLogin(rs.getString("U_LOGIN"));
                user.setId(rs.getLong("U_ID"));
                comment.setUser(user);

                comments.add(comment);
            }

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return comments;
    }


}
