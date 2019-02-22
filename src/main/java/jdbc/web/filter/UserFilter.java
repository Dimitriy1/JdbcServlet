package jdbc.web.filter;

import jdbc.config.Factory;
import jdbc.dao.UserDao;
import jdbc.model.Role;
import jdbc.model.TypeOfRole;
import jdbc.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserFilter implements Filter {
    private UserDao userDao;
    private Map<String, Role> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        userDao = Factory.getUserDao();
        protectedUrls.put("/servlet/user", new Role(TypeOfRole.USER));
        protectedUrls.put("/servlet/admin", new Role(TypeOfRole.ADMIN));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String path = req.getServletPath() + req.getPathInfo();

        Cookie[] cookies = req.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("MATE")) {
                    token = c.getValue();
                }
            }
        }

        if (token == null) {
            if (path.equals("/servlet/login")
                    || path.equals("/servlet/register")
                    || path.equals("/servlet/home")
                    || path.equals("/servlet/user")
                    || path.equals("/servlet/admin")
                    || path.equals("/servlet/403")){
                processAuthorized(servletRequest, servletResponse, filterChain);
            } else {
                processUnAuthorized(req, res);
            }
        } else {
            User user = userDao.findByToken(token);

            if (user == null) {
                processUnAuthorized(req, res);
            } else {
                if (verifyRole(user, path)) {
                    processAuthorized(req, res, filterChain);
                } else {
                    processDenied(req, res, filterChain);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    private void processUnAuthorized(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(req, res);
    }

    private void processAuthorized(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(req, res);
    }

    private void processDenied(HttpServletRequest req,
                               HttpServletResponse res, FilterChain chain) throws IOException {
        String redirectUrl = "/Lab8_war_exploded/servlet/403";
        res.sendRedirect(redirectUrl);
    }

    private boolean verifyRole(User user, String path) {
        Role pathRole = protectedUrls.get(path);
        Set<Role> userRoles = user.getRoles();
        boolean isVerified = false;

        if (pathRole == null) {
            return true;
        }

        for (Role userRole : userRoles) {
            if (pathRole.getRole().equals(userRole.getRole())) {
                isVerified = true;
                break;
            }
        }

        return isVerified;
    }
}
