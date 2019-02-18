package jdbc.controllers;

import jdbc.NoUniqueException;
import jdbc.model.User;
import jdbc.service.UserService;
import jdbc.web.Request;
import jdbc.web.ViewModel;

import java.util.UUID;

import static jdbc.encoder.PasswordEncoder.encrypt;

public class RegisterController implements Controller {
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        String login = request.getParameterByName("signup-login");
        String password = request.getParameterByName("signup-password");
        String email = request.getParameterByName("signup-email");
        String name = request.getParameterByName("signup-user");

        User user = new User();
        user.setLogin(login);
        user.setPassword(encrypt(password,"20"));
        user.setEmail(email);
        user.setName(name);
        user.setToken(getRandomToken());

        if (userService.isLoggedIn(user.getLogin(), user.getPassword()) == null) {
            userService.insertUser(user);
        } else {
            throw new NoUniqueException("User that you wanted to register is already exist");
        }

        return ViewModel.of("login");
    }

    private String getRandomToken() {
        return UUID.randomUUID().toString();
    }
}
