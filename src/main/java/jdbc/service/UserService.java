package jdbc.service;

import jdbc.model.Role;
import jdbc.model.User;

public interface UserService {
    void insertUser(User user);

    User isLoggedIn(String login, String password);

    void addRoleForUser(User user, Role role);
}
