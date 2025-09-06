package jpa.controllers.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.entity.User;
import jpa.services.UserService;
import jpa.services.impl.UserServiceImpl;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userService.checkLogin(username, password);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            String ctx = req.getContextPath();
            switch (user.getRoleId()) {
                case 1 -> resp.sendRedirect(ctx + "/user/home");
                case 2 -> resp.sendRedirect(ctx + "/manager/home");
                case 3 -> resp.sendRedirect(ctx + "/admin/home");
                default -> {
                    req.setAttribute("error", "Invalid role");
                    doGet(req, resp);
                }
            }
        } else {
            req.setAttribute("error", "Invalid username or password");
            doGet(req, resp);
        }
    }
}