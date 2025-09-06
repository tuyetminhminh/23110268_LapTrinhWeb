package jpa.controllers.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.entity.User;
import jpa.services.UserService;
import jpa.services.impl.UserServiceImpl;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");

        if (userService.findByUsername(username) != null) {
            req.setAttribute("error", "Username already exists");
            doGet(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Nên hash password ở production
        user.setFullname(fullname);
        user.setEmail(email);
        user.setRoleId(1); // Default user
        userService.insert(user);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}