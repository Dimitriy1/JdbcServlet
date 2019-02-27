package jdbc.dao;

import jdbc.MyException;
import jdbc.model.Role;
import jdbc.model.Table;
import jdbc.model.TypeOfRole;
import jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class RoleDaoImpl extends AbstractDao implements RoleDao {
    public RoleDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void addRole(Role role) {
        final String insertIntoRole = "INSERT INTO role(name) value (?)";
        final String insertIntoUserRole = "INSERT INTO user_role(user_id,role_id) values (?, ?)";
        Integer maxId = null;

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertIntoRole);
            preparedStatement.setString(1, String.valueOf(role.getRole()));
            preparedStatement.executeUpdate();

            maxId = findMaxId(Table.ROLE);
            role.setId(maxId);

            Set<User> users = role.getUsers();
            for (User user : users) {
                preparedStatement = connection
                        .prepareStatement(insertIntoUserRole);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setInt(2, maxId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }

    @Override
    public Set<Role> getRoles() {
        Set<Role> roles = new LinkedHashSet<>();
        final String selectRoles = "SELECT * FROM role";

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(selectRoles);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRole(TypeOfRole.valueOf(rs.getString("name")));
                roles.add(role);
            }

            return roles;

        } catch (SQLException e) {
            throw new MyException(e, "something went wrong");
        }
    }
}
