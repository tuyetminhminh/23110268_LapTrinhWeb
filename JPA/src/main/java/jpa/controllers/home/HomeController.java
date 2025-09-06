//package jpa.controllers.home;
//
//import java.io.IOException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jpa.entity.Category;
//import jpa.entity.User;
//import jpa.services.CategoryService;
//import jpa.services.UserService;
//import jpa.services.impl.CategoryServiceImpl;
//import jpa.services.impl.UserServiceImpl;
//import java.util.List;
//
//@WebServlet(urlPatterns = { "/admin/home", "/manager/home", "/user/home" })
//public class HomeController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private final CategoryService categoryService = new CategoryServiceImpl();
//    private final UserService userService = new UserServiceImpl();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        User currentUser = (User) req.getSession().getAttribute("currentUser");
//        if (currentUser == null) {
//            resp.sendRedirect(req.getContextPath() + "/login");
//            return;
//        }
//
//        List<Category> listcate = categoryService.findAll();
//        req.setAttribute("listcate", listcate);
//        req.getRequestDispatcher("/views/category/list.jsp").forward(req, resp);
//    }
//}
package jpa.controllers.home;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jpa.entity.Category;
import jpa.entity.User;
import jpa.services.CategoryService;
import jpa.services.UserService;
import jpa.services.impl.CategoryServiceImpl;
import jpa.services.impl.UserServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = { "/admin/home", "/manager/home", "/user/home" })
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        User currentUser = (User) req.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Category> listcate;
        String userIdStr = req.getParameter("userId"); // Lấy userId từ request (nếu có)
        Integer userId = (userIdStr != null && !userIdStr.isEmpty()) ? Integer.parseInt(userIdStr) : null;
        
        if (currentUser.getRoleId() == 3 || currentUser.getRoleId() == 1) { // Admin hoặc User
            listcate = categoryService.findAll(); // Hiển thị tất cả
        } else if (currentUser.getRoleId() == 2) { // Manager
        	if (userId != null) {
                // Lọc category theo userId được chọn
                listcate = categoryService.findAll().stream()
                        .filter(c -> c.getUser().getId().equals(userId))
                        .collect(Collectors.toList());
            } else {
                // Nếu không chọn userId, mặc định hiển thị category của chính manager
            	listcate = categoryService.findAll().stream()
                    .filter(c -> c.getUser().getId().equals(currentUser.getId())) // Chỉ category của manager
                    .collect(Collectors.toList());
            }
        } else {
            listcate = List.of(); // Trường hợp role không xác định
        }
        
    	// Lấy danh sách user để hiển thị trong dropdown (chỉ cho Manager)
        List<User> userList = (currentUser.getRoleId() == 2) ? userService.findAll() : null;
        req.setAttribute("userList", userList);
        req.setAttribute("listcate", listcate);
        req.getRequestDispatcher("/views/category/list.jsp").forward(req, resp);
    }
}