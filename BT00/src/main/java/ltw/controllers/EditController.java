package ltw.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ltw.models.Category;
import ltw.models.User;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/category/edit")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5L * 1024 * 1024,
    maxRequestSize = 10L * 1024 * 1024
)
public class EditController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CategoryService service = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

        int id = Integer.parseInt(req.getParameter("id"));
        var c = service.get(id, u.getId());
        if (c == null) { resp.sendError(404); return; }
        req.setAttribute("category", c);
        req.setAttribute("pageTitle", "Sửa danh mục");
        req.setAttribute("active", "category");
        req.setAttribute("contentPage", "/views/category/edit.jsp");
        req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

        req.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");

        // Lấy category hiện tại để biết icon cũ
        var current = service.get(id, u.getId());
        if (current == null) { resp.sendError(404); return; }
        String icons = current.getIcons();

        Part filePart = req.getPart("iconFile");
        if (filePart != null && filePart.getSize() > 0) {
            if (filePart.getContentType() == null || !filePart.getContentType().startsWith("image/")) {
                req.setAttribute("error", "Chỉ cho phép upload ảnh.");
                req.setAttribute("category", current);
                req.getRequestDispatcher("/views/category/edit.jsp").forward(req, resp);
                return;
            }

            String ext = guessExt(filePart.getSubmittedFileName());
            String savedName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

            String uploadDir = getServletContext().getRealPath("/uploads");
            new File(uploadDir).mkdirs();

            // Lưu file mới
            File dest = new File(uploadDir, savedName);
            try (var in = filePart.getInputStream()) {
                Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // (tuỳ chọn) xóa file cũ nếu có
            if (icons != null) {
                new File(uploadDir, icons).delete();
            }
            icons = savedName;
        }

        Category c = new Category();
        c.setCateId(id);
        c.setCateName(name);
        c.setIcons(icons);           // giữ cũ nếu không upload mới
        c.setUserId(u.getId());
        service.edit(c);

        resp.sendRedirect(req.getContextPath()+"/category/list");
    }

    private static String guessExt(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        return (dot >= 0) ? filename.substring(dot).toLowerCase() : "";
    }
}
