package jdbc.controllers;

import jdbc.service.UserService;
import jdbc.web.Request;
import jdbc.web.ViewModel;

public class HomeController implements Controller {
    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        ViewModel viewModel = new ViewModel("home");
        return viewModel;
    }
}
