package jdbc.dao;

import jdbc.model.Role;
import jdbc.model.User;

public interface UserDao {
    void insertUser(User user);

    User findByLogin(String login);

    User findByToken(String token);

    void addRoleForUser(User user, Role role);

}

