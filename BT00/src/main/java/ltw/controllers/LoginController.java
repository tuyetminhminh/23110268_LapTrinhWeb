package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ltw.models.User;
import ltw.service.UserServiceImpl;
import ltw.util.Constant;
import ltw.service.UserService;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
			resp.sendRedirect(req.getContextPath() + "/waiting");
			return;
		}
		// Check cookie "username" (remember me)
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
					session = req.getSession(true);
					session.setAttribute(Constant.SESSION_USERNAME, c.getValue());
					resp.sendRedirect(req.getContextPath() + "/waiting");
					return;
				}
			}
		}
		req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String next = req.getParameter("next"); // nếu có

		UserService svc = new UserServiceImpl();
		User user = svc.login(username, password);
		if (user == null) {
			req.setAttribute("error", "Sai tài khoản hoặc mật khẩu");
			req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
			return;
		}

		req.getSession().setAttribute("currentUser", user);

		// Ưu tiên quay lại trang "next" nếu có, an toàn trong cùng domain
		if (next != null && next.startsWith(req.getContextPath())) {
			resp.sendRedirect(next);
		} else {
			resp.sendRedirect(req.getContextPath() + "/home");
		}
	}
}