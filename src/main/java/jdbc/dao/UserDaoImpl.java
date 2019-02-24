package jdbc.dao;

import jdbc.MyException;
import jdbc.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class UserDaoImpl extends AbstractDao implements UserDao {
    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void insertUser(User user) {
        final String insertIntoUser = "INSERT INTO user(name,login,password,email,token) values (?, ?, ?, ?, ?)";
        final String insertIntoUserRole = "INSERT INTO user_role(user_id,role_id) value (?, ?)";
        Integer maxId = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertIntoUser);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.executeUpdate();

            maxId = findMaxId(Table.USER);
            user.setId(maxId);

            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                preparedStatement = connection
                        .prepareStatement(insertIntoUserRole);
                preparedStatement.setInt(1, maxId);
                preparedStatement.setInt(2, role.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    @Override
    public void addRoleForUser(User user, Role role) {
        final String insertIntoUserConcreteRole = "INSERT INTO user_role(user_id,role_id) value (?, ?)";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertIntoUserConcreteRole);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, role.getId());
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
        final String getRolesOfConcreteUser = "SELECT * FROM role " +
                "INNER JOIN user_role " +
                "ON role.id = user_role.role_id " +
                "INNER JOIN user " +
                "ON user.id = user_role.user_id " +
                "WHERE user.id = ?";

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

                preparedStatement = connection
                        .prepareStatement(getRolesOfConcreteUser);
                preparedStatement.setInt(1, user.getId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Role role = new Role();
                    role.setId(rs.getInt("id"));
                    role.setRole(TypeOfRole.valueOf(rs.getString("name")));
                    user.addRole(role);
                }

                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }
}
