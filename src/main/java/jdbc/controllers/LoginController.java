package jdbc.controllers;

import jdbc.model.User;
import jdbc.service.UserService;
import jdbc.web.Request;
import jdbc.web.ViewModel;

import javax.servlet.http.Cookie;

import static jdbc.encoder.PasswordEncoder.encrypt;


public class LoginController implements Controller {
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        String login = request.getParameterByName("login-user");
        String password = request.getParameterByName("login-password");
        User user = userService.isLoggedIn(login, encrypt(password,"20"));
        
        if (user != null) {
            return processAuthorize(user);
        } else {
            return processUnAuthorize();
        }
    }

    private ViewModel processAuthorize(User user) {
        String userToken = user.getToken();
        Cookie cookie = new Cookie("MATE", userToken);
        ViewModel vm = new ViewModel("home");
        vm.addCookie(cookie);
        return vm;

    }

    private ViewModel processUnAuthorize() {
        ViewModel vm = new ViewModel("login");
        vm.addAttribute("err", "Incorrect login/password");
        return ViewModel.of("login");
    }
}
