package jpa.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpa.entity.User;

@WebFilter(urlPatterns = { "/user/*", "/manager/*", "/admin/*" })
public class RoleFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		HttpSession session = req.getSession(false);
		User u = (session == null) ? null : (User) session.getAttribute("currentUser");
		if (u == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String path = req.getServletPath(); // ví dụ: /manager/category/add
		int role = u.getRoleId() == null ? 0 : u.getRoleId();

		boolean allowed = (path.startsWith("/user/")) || (path.startsWith("/manager/") && (role == 2 || role == 3))
				|| (path.startsWith("/admin/") && role == 3);

		// user (1) tự động được phép /user/*
		if (role == 1 && !path.startsWith("/user/"))
			allowed = false;

		if (!allowed) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		chain.doFilter(request, response);
	}
}
