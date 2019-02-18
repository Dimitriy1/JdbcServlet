package jdbc.config;

import jdbc.ConnectionUtil;
import jdbc.controllers.LoginController;
import jdbc.controllers.RegisterController;
import jdbc.dao.UserDao;
import jdbc.dao.UserDaoImpl;
import jdbc.service.UserService;
import jdbc.service.UserServiceImpl;

import java.sql.Connection;

public class Factory {
    private final static Connection CONNECTION;

    private Factory() {
    }

    static {
        CONNECTION = ConnectionUtil.getConnection();
    }

    public static RegisterController getRegisterController() {
        return new RegisterController(getUserService());
    }

    public static LoginController getLoginController() {
        return new LoginController(getUserService());
    }

    public static UserService getUserService() {
        return new UserServiceImpl(getUserDao());

    }

    public static UserDao getUserDao() {
        return new UserDaoImpl(CONNECTION);
    }
}
