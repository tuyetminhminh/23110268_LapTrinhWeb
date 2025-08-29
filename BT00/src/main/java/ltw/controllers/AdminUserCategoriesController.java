package ltw.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltw.dao.impl.UserDaoImpl;
import ltw.models.Category;
import ltw.models.User;
import ltw.dao.UserDao;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/admin/user/categories")
public class AdminUserCategoriesController extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final CategoryService cateService = new CategoryServiceImpl();
  private final UserDao userDao = new UserDaoImpl();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    int userId = Integer.parseInt(req.getParameter("userId"));
    User target = userDao.findById(userId);  // thêm method findById nếu chưa có
    if (target == null) {
        resp.sendError(404, "User not found");
        return;
    }
    // lấy danh mục của user
    List<Category> categories = cateService.getAllByUser(userId);

    req.setAttribute("targetUser", target);
    req.setAttribute("cateList", cateService.getAllByUser(userId));

    req.setAttribute("pageTitle", "Danh mục của " + target.getUserName());
    req.setAttribute("active", "admin");
    req.setAttribute("contentPage", "/views/admin/user_categories.jsp");
    req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
  }
}

