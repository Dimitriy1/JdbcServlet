package jdbc.web;

import jdbc.config.Factory;
import jdbc.controllers.Controller;
import jdbc.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static jdbc.web.Request.RequestMethod.GET;
import static jdbc.web.Request.RequestMethod.POST;

public class MainServlet extends HttpServlet {
    private final static Map<Request, Controller> controllers = new HashMap<>();

    static {
        controllers.put(Request.of("/servlet/login", GET),
                r -> ViewModel.of("login"));
        controllers.put(Request.of("/servlet/login", POST),
                Factory.getLoginController());
        controllers.put(Request.of("/servlet/register", GET),
                r -> ViewModel.of("register"));
        controllers.put(Request.of("/servlet/register", POST),
                Factory.getRegisterController());
        controllers.put(Request.of("/servlet/home", GET),
                r -> ViewModel.of("home"));
        controllers.put(Request.of("/servlet/admin", GET),
                r -> ViewModel.of("admin"));
        controllers.put(Request.of("/servlet/user", GET),
                r -> ViewModel.of("user"));
        controllers.put(Request.of("/servlet/403", GET),
                r -> ViewModel.of("403"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        process(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        process(req, res);
    }

    private void sendResponse(ViewModel vm, HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String redirectUrl = "/WEB-INF/views/%s.jsp";
        String path = req.getServletPath() + req.getPathInfo();

        List<Cookie> cookies = vm.getCookies();
        for (Cookie cookie : cookies) {
            res.addCookie(cookie);
        }

        if (isPressedLoginFormButton(req, path, vm)) {
            req.getSession().setAttribute("LOGGED_USER", new User());
            res.sendRedirect("/Lab8_war_exploded/servlet/home");
        } else if (isRegistered(req, path)) {
            res.sendRedirect("/Lab8_war_exploded/servlet/login");
        } else if (isNoRegistered(req, path)) {
            res.sendRedirect("/Lab8_war_exploded/servlet/login");
        } else if (isLoggedIn(req, path)) {
            res.sendRedirect("/Lab8_war_exploded/servlet/home");
        } else {
            req.getRequestDispatcher(String.format(redirectUrl, vm.getView()))
                    .forward(req, res);
        }
    }

    private boolean isPressedLoginFormButton(HttpServletRequest req, String path, ViewModel vm) {
        return req.getMethod().equals("POST") && path.equals("/servlet/login") && vm.getView().equals("home");
    }

    private boolean isRegistered(HttpServletRequest req, String path) {
        return req.getMethod().equals("POST") && path.equals("/servlet/register");
    }

    private boolean isNoRegistered(HttpServletRequest req, String path) {
        return req.getMethod().equals("GET")
                && (path.equals("/servlet/home") || path.equals("/servlet/user")
                || path.equals("/servlet/admin") || path.equals("/servlet/403"))
                && req.getSession().getAttribute("LOGGED_USER") == null;
    }

    private boolean isLoggedIn(HttpServletRequest req, String path) {
        return req.getSession().getAttribute("LOGGED_USER") != null
                && (path.equals("/servlet/register") || path.equals("/servlet/login"))
                && req.getMethod().equals("GET");
    }

    private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String path = req.getServletPath() + req.getPathInfo();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Request r = Request.of(path, Request.RequestMethod.valueOf(req.getMethod()), parameterMap);
        Controller controller = controllers.get(r);
        ViewModel vm = controller.process(r);
        sendResponse(vm, req, res);
    }
}
