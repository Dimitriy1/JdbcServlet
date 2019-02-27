package jdbc.controllers;

import jdbc.NoUniqueException;
import jdbc.model.Role;
import jdbc.model.TypeOfRole;
import jdbc.model.User;
import jdbc.service.RoleService;
import jdbc.service.UserService;
import jdbc.web.Request;
import jdbc.web.ViewModel;

import java.util.UUID;

import static jdbc.encoder.PasswordEncoder.encrypt;

public class RegisterController implements Controller {
    private UserService userService;
    private RoleService roleService;

    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
        user.setRoles(roleService.getRoles());

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
