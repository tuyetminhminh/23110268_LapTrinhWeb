package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ltw.models.User;
import ltw.util.Constant;

@WebServlet(urlPatterns = "/waiting")
public class WaitingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Lấy session hiện tại, nếu chưa có thì trả về null (không tạo mới)
        HttpSession session = req.getSession(false);

        // Nếu đã đăng nhập (có session + có account)
        if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
            User user = (User) session.getAttribute(Constant.SESSION_ACCOUNT);

            // Điều hướng theo roleid
            switch (user.getRoleid()) {
                case 1: // Admin
                    resp.sendRedirect(req.getContextPath() + "/home");
                    break;
                case 2: // Manager
                    resp.sendRedirect(req.getContextPath() + "/home");
                    break;
                default: // User thường
                    resp.sendRedirect(req.getContextPath() + "/home");
                    break;
            }
        } else {
            // Nếu chưa đăng nhập thì về trang login
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
