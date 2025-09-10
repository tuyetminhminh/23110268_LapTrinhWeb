package ltw.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login-cookie")
public class HiControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		// Giả sử user: admin, pass: 123
		if ("admin".equals(user) && "123".equals(pass)) {
			// Tạo cookie
			Cookie ckUser = new Cookie("username", user);
			ckUser.setMaxAge(60 * 5); // 5 phút
			resp.addCookie(ckUser);

			out.println("<h3>Login thành công bằng Cookie</h3>");
			out.println("<a href='welcome-cookie'>Vào trang Welcome</a>");
		} else {
			out.println("<h3>Login thất bại</h3>");
		}
	}
}
