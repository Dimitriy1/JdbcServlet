package jdbc.dao;

import jdbc.MyException;
import jdbc.model.LoginToken;
import jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void insertUser(User user) {
        final String insertIntoUserReg = "INSERT INTO user(name,login,password,email,token) values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertIntoUserReg);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    @Override
    public User findByLogin(String login) {
        return findBy(LoginToken.LOGIN, login);
    }

    @Override
    public User findByToken(String token) {
        return findBy(LoginToken.TOKEN, token);
    }

    private User findBy(LoginToken type, String loginToken) {
        final String getUserByLogin = "SELECT * FROM user WHERE "
                + String.valueOf(type).toLowerCase() + " = ?";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(getUserByLogin);
            preparedStatement.setString(1, loginToken);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setToken(rs.getString("token"));

                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }
}
