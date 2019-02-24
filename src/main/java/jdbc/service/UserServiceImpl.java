package jdbc.service;

import jdbc.dao.UserDao;
import jdbc.model.Role;
import jdbc.model.User;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public User isLoggedIn(String login, String password) {
        User user = userDao.findByLogin(login);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void addRoleForUser(User user, Role role) {
        userDao.addRoleForUser(user, role);
    }
}
