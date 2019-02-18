package jdbc.web.filter;

import jdbc.config.Factory;
import jdbc.dao.UserDao;
import jdbc.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {
    private UserDao userDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = Factory.getUserDao();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String path = req.getServletPath() + req.getPathInfo();
        
        if (req.getSession().getAttribute("LOGGED_USER") != null) {
            req.getRequestDispatcher("/WEB-INF/views/home.jsp")
                    .forward(req, res);
            return;
        }

        Cookie[] cookies = req.getCookies();
        String token = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("MATE")) {
                token = c.getValue();
            }
        }

        if (token == null) {
            if (path.equals("/servlet/login") || path.equals("/servlet/register")) {
                processAuthorized(servletRequest, servletResponse, filterChain);
            } else {
                processUnAuthorized(req, res);
            }
        } else {
            User user = userDao.findByToken(token);
            if (user == null) {
                processUnAuthorized(req, res);
            } else {
                processAuthorized(servletRequest, servletResponse, filterChain);
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
}
