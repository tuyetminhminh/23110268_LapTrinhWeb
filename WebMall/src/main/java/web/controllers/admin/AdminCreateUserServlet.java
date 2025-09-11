package web.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

@WebServlet("/admin/create-user")
public class AdminCreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("currentUser") : null);
        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/views/admin-create-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User admin = (User) (session != null ? session.getAttribute("currentUser") : null);
        if (admin == null || !"admin".equalsIgnoreCase(admin.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");
        UserDao userDao = new UserDaoImpl();
        if (userDao.findByUsername(username) != null) {
            req.setAttribute("error", "Username already exists.");
            req.getRequestDispatcher("/views/admin-create-user.jsp").forward(req, resp);
            return;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRole(role);
        userDao.save(user);
        req.setAttribute("success", "User created successfully.");
        req.getRequestDispatcher("/views/admin-create-user.jsp").forward(req, resp);
    }
}
