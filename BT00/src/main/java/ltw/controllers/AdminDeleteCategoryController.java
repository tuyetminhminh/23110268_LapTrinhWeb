package ltw.controllers;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ltw.models.Category;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/admin/user/category/delete")
public class AdminDeleteCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService service = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int id     = Integer.parseInt(req.getParameter("id"));

        // Lấy để xóa file icon
        Category c = service.get(id, userId);
        if (c != null && c.getIcons() != null) {
            String uploadDir = getServletContext().getRealPath("/uploads");
            new File(uploadDir, c.getIcons()).delete();
        }

        service.delete(id, userId);
        resp.sendRedirect(req.getContextPath() + "/admin/user/categories?userId=" + userId);
    }
}
