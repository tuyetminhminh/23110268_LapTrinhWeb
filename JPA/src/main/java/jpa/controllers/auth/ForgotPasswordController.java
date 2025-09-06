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

@WebServlet("/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        User user = userService.findByEmail(email);
        if (user != null) {
            // Logic reset password: Giả lập gửi email, hoặc update password random
            String newPass = "newpassword"; // Thay bằng logic thực (gửi email)
            user.setPassword(newPass);
            userService.update(user);
            req.setAttribute("message", "Password reset sent to email");
        } else {
            req.setAttribute("error", "Email not found");
        }
        doGet(req, resp);
    }
}