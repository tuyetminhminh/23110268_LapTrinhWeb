package web.controllers;

import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        UserDao userDao = new UserDaoImpl();
        User user = userDao.findByUsername(username);
        if (user != null) {
            user.setPassword("newpassword");
            userDao.update(user);
            req.setAttribute("success", "Mật khẩu đã được đặt lại về 'newpassword'. Vui lòng đăng nhập lại.");
        } else {
            req.setAttribute("error", "Không tìm thấy tài khoản với tên đăng nhập này.");
        }
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }
}
