package jpa.controllers.category;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jpa.entity.Category;
import jpa.entity.User;
import jpa.services.CategoryService;
import jpa.services.UserService;
import jpa.services.impl.CategoryServiceImpl;
import jpa.services.impl.UserServiceImpl;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/category/*", "/manager/category/*", "/user/category/*"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Giới hạn 5MB, chỉ áp dụng khi cần upload
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private static final String IMAGE_DIR = "images";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if ("/add".equals(pathInfo)) {
            List<User> userList = (currentUser.getRoleId() == 3) ? userService.findAll() :
                    (currentUser.getRoleId() == 2) ? userService.findAll().stream()
                            .filter(u -> u.getRoleId() == 1 || u.getId() == currentUser.getId())
                            .toList() : List.of(currentUser);
            req.setAttribute("userList", userList);
            req.setAttribute("category", new Category());
            req.getRequestDispatcher("/views/category/form.jsp").forward(req, resp);
        } else if ("/edit".equals(pathInfo)) {
            String idStr = req.getParameter("id");
            if (idStr == null || idStr.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
                return;
            }
            Integer id = Integer.parseInt(idStr);
            Category category = categoryService.findById(id);
            if (category == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            // Phân quyền sửa
            boolean hasEditPermission = false;
            if (currentUser.getRoleId() == 3) { // Admin sửa tất cả
                hasEditPermission = true;
            } else if (currentUser.getRoleId() == 2) { // Manager sửa tất cả ngoại trừ category của Admin
                hasEditPermission = (category.getUser().getRoleId() != 3);
            } else if (currentUser.getRoleId() == 1) { // User chỉ sửa category của chính mình
                hasEditPermission = category.getUser().getId().equals(currentUser.getId());
            }

            if (!hasEditPermission) {
                req.setAttribute("error", "Bạn không có quyền sửa category này!");
                req.getRequestDispatcher("/views/category/detail.jsp").forward(req, resp);
                return;
            }
            List<User> userList = (currentUser.getRoleId() == 3) ? userService.findAll() : List.of(currentUser);
            req.setAttribute("userList", userList);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/views/category/form.jsp").forward(req, resp);
        } else if ("/detail".equals(pathInfo)) {
            // Chuyển sang DetailCategoryController xử lý
            resp.sendRedirect(req.getContextPath() + "/" + (currentUser.getRoleId() == 3 ? "admin" : currentUser.getRoleId() == 2 ? "manager" : "user") + "/category/detail?id=" + req.getParameter("id"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idStr = req.getParameter("id");
        Integer id = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
        String categoryname = req.getParameter("categoryname");
        String userIdStr = req.getParameter("userId");
        Part filePart = null;

        Category category = (id != null) ? categoryService.findById(id) : new Category();
        if (id != null && category == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Kiểm tra quyền trước khi thực hiện hành động
        if (id != null) {
            boolean hasEditPermission = false;
            if (currentUser.getRoleId() == 3) { // Admin sửa/xóa tất cả
                hasEditPermission = true;
            } else if (currentUser.getRoleId() == 2) { // Manager sửa/xóa tất cả ngoại trừ category của Admin
                hasEditPermission = (category.getUser().getRoleId() != 3);
            } else if (currentUser.getRoleId() == 1) { // User chỉ sửa/xóa category của chính mình
                hasEditPermission = category.getUser().getId().equals(currentUser.getId());
            }
            if (!hasEditPermission) {
                req.setAttribute("error", "Bạn không có quyền chỉnh sửa hoặc xóa category này!");
                req.getRequestDispatcher("/views/category/detail.jsp").forward(req, resp);
                return;
            }
        }

        category.setCategoryname(categoryname);
        User ownerUser = (userIdStr != null && !userIdStr.isEmpty() && currentUser.getRoleId() == 3) ?
                userService.findById(Integer.parseInt(userIdStr)) : currentUser;
        category.setUser(ownerUser);

        // Chỉ lấy filePart nếu request là multipart (thêm hoặc sửa)
        if (req.getContentType() != null && req.getContentType().toLowerCase().startsWith("multipart/")) {
            filePart = req.getPart("images");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                String uploadPath = getServletContext().getRealPath("") + File.separator + IMAGE_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                filePart.write(uploadPath + File.separator + fileName);
                category.setImages(req.getContextPath() + "/" + IMAGE_DIR + "/" + fileName);
            }
        }

        try {
            if (id == null) {
                categoryService.insert(category);
                req.getSession().setAttribute("message", "Thêm category thành công!");
            } else if (categoryname != null && !categoryname.isEmpty()) { // Sửa
                categoryService.update(category);
                req.getSession().setAttribute("message", "Cập nhật category thành công!");
            } else { // Xóa
                categoryService.delete(id);
                req.getSession().setAttribute("message", "Xóa category thành công!");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Lỗi khi xử lý category: " + e.getMessage());
            req.getRequestDispatcher("/views/category/detail.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/" + (currentUser.getRoleId() == 3 ? "admin" : currentUser.getRoleId() == 2 ? "manager" : "user") + "/home");
    }
}