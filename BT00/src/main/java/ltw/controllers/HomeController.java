package ltw.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ltw.models.User;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

import java.io.IOException;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("currentUser");
        if (u == null) {
            // khách vãng lai
            req.setAttribute("pageTitle", "Chào mừng");
            req.setAttribute("active", "home");
            req.setAttribute("contentPage", "/views/home/landing.jsp");
            req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
            return;
        }

        // bơm category của user
        req.setAttribute("myCategories", categoryService.getAllByUser(u.getId()));

        // chọn trang con theo role
        String content = switch (u.getRoleid()) {
            case 1 -> "/views/home/admin.jsp";
            case 2 -> "/views/home/manager.jsp";
            default -> "/views/home/user.jsp";
        };

        req.setAttribute("pageTitle", "Trang chủ");
        req.setAttribute("active", "home");
        req.setAttribute("contentPage", content);

        // FORWARD QUA LAYOUT (layout có link CSS + navbar + logout)
        req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
    }
}

