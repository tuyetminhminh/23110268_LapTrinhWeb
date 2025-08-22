package ltw.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/welcome-cookie")
public class WelcomeControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();

		Cookie[] cookies = req.getCookies();
		String user = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("username")) {
					user = c.getValue();
				}
			}
		}

		if (user != null) {
			out.println("<h3>Xin chào " + user + ", bạn đã login bằng Cookie</h3>");
		} else {
			out.println("<h3>Chưa login</h3>");
		}
	}
}
