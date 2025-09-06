package jpa.controllers.category;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.entity.Category;
import jpa.entity.User;
import jpa.services.CategoryService;
import jpa.services.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/admin/category/detail", "/manager/category/detail", "/user/category/detail"})
public class DetailCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Integer id = Integer.parseInt(req.getParameter("id"));
        Category category = categoryService.findById(id);
        if (category == null) {
            resp.sendError(404);
            return;
        }

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Phân quyền xem chi tiết
        boolean hasPermission = false;
        if (currentUser.getRoleId() == 3) { // Admin xem tất cả
            hasPermission = true;
        } else if (currentUser.getRoleId() == 2) { // Manager xem tất cả ngoại trừ category của Admin
            hasPermission = (category.getUser().getRoleId() != 3);
        } else if (currentUser.getRoleId() == 1) { // User chỉ xem category của chính mình
            hasPermission = category.getUser().getId().equals(currentUser.getId());
        }

        if (!hasPermission) {
            req.setAttribute("error", "Bạn không có quyền xem chi tiết category này!");
        }

        req.setAttribute("category", category);
        req.getRequestDispatcher("/views/category/detail.jsp").forward(req, resp);
    }
}