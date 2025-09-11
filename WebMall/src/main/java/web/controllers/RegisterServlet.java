package web.controllers;

import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        UserDao userDao = new UserDaoImpl();
        if (userDao.findByUsername(username) != null) {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRole("user");
        userDao.save(user);
        req.setAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }
}
