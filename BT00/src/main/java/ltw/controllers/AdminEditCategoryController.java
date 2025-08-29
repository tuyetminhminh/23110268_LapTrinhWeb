package ltw.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ltw.models.Category;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/admin/user/category/edit")
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 5L * 1024 * 1024,
    maxRequestSize = 10L * 1024 * 1024
)
public class AdminEditCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService service = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int id     = Integer.parseInt(req.getParameter("id"));

        Category c = service.get(id, userId);
        if (c == null) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }

        req.setAttribute("targetUserId", userId);
        req.setAttribute("category", c);

        req.setAttribute("pageTitle", "Sửa danh mục (Admin)");
        req.setAttribute("active", "admin");
        req.setAttribute("contentPage", "/views/admin/category_edit.jsp");
        req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(req.getParameter("userId"));
        int id     = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");

        Category current = service.get(id, userId);
        if (current == null) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }

        // xử lý đổi icon (nếu upload)
        String icons = current.getIcons();
        Part filePart = req.getPart("iconFile");
        if (filePart != null && filePart.getSize() > 0) {
            if (filePart.getContentType() == null || !filePart.getContentType().startsWith("image/")) {
                req.setAttribute("error", "Chỉ cho phép upload ảnh.");
                req.setAttribute("targetUserId", userId);
                req.setAttribute("category", current);
                req.setAttribute("pageTitle", "Sửa danh mục (Admin)");
                req.setAttribute("active", "admin");
                req.setAttribute("contentPage", "/views/admin/category_edit.jsp");
                req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
                return;
            }
            String ext = getExt(filePart.getSubmittedFileName());
            String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

            String uploadDir = getServletContext().getRealPath("/uploads");
            new File(uploadDir).mkdirs();
            File dest = new File(uploadDir, savedName);
            try (var in = filePart.getInputStream()) {
                Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            // Xóa ảnh cũ nếu có
            if (icons != null) new File(uploadDir, icons).delete();
            icons = savedName;
        }

        Category c = new Category();
        c.setCateId(id);
        c.setCateName(name);
        c.setIcons(icons);
        c.setUserId(userId);

        service.edit(c);
        resp.sendRedirect(req.getContextPath() + "/admin/user/categories?userId=" + userId);
    }

    private static String getExt(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot >= 0) ? filename.substring(dot).toLowerCase() : "";
    }
}
