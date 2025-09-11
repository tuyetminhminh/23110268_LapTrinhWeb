package web.controllers;

import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check for remember-me cookie
        String rememberedUsername = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("rememberMe".equals(c.getName())) {
                    rememberedUsername = c.getValue();
                    break;
                }
            }
        }
        req.setAttribute("rememberedUsername", rememberedUsername);
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            req.getSession().setAttribute("currentUser", user);
            // Handle remember me
            if ("on".equals(remember)) {
                Cookie cookie = new Cookie("rememberMe", username);
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("rememberMe", "");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            // Redirect by role
            if ("admin".equalsIgnoreCase(user.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/profile");
            }
        } else {
            req.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}