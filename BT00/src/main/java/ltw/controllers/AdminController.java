package ltw.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ltw.dao.UserDao;
import ltw.dao.impl.UserDaoImpl;
import ltw.models.User;

@WebServlet("/admin/users")
public class AdminController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final UserDao userDao = new UserDaoImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Ở AuthFilter đã chặn quyền, nên ở đây giả định đã là admin
    List<User> users = userDao.findAll();   // thêm method này ở UserDao/UserDaoImpl
    req.setAttribute("users", users);

    req.setAttribute("pageTitle", "Quản lý người dùng");
    req.setAttribute("active", "admin");
    req.setAttribute("contentPage", "/views/admin/users.jsp");
    req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
  }
}
