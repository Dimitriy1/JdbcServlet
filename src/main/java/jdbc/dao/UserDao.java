package jdbc.dao;

import jdbc.model.LoginToken;
import jdbc.model.User;

public interface UserDao {
    void insertUser(User user);

    User findByLogin(String login);

    User findByToken(String token);
}

