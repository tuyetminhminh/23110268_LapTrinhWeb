package ltw.filters;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltw.models.User;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;
	private static final Set<String> PUBLIC = Set.of("/home", "/login", "/register", "/forgot", "/reset");
	private static final Set<String> STATIC = Set.of("/css/", "/js/", "/images/", "/uploads/", "/favicon.ico");

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String path = req.getRequestURI().substring(req.getContextPath().length());

		// 1) Static qua thẳng
		for (String p : STATIC)
			if (path.startsWith(p)) {
				chain.doFilter(req, res);
				return;
			}

		// 2) Public qua thẳng
		for (String p : PUBLIC)
			if (path.equals(p) || path.startsWith(p + "/")) {
				chain.doFilter(req, res);
				return;
			}

		// 3) Còn lại phải đăng nhập
		User u = (User) req.getSession().getAttribute("currentUser");
		if (u == null) {
			String next = req.getRequestURI() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
			res.sendRedirect(req.getContextPath() + "/login?next=" + URLEncoder.encode(next, StandardCharsets.UTF_8));
			return;
		}
		// Chặn quyền admin cho /admin/*
		if (path.startsWith("/admin/") && u.getRoleid() != 1) {
		  res.sendError(HttpServletResponse.SC_FORBIDDEN);
		  return;
		}
		// Chặn quyền admin cho /admin/*
		if (path.startsWith("/manager/") && u.getRoleid() != 1) {
		  res.sendError(HttpServletResponse.SC_FORBIDDEN);
		  return;
		}
		chain.doFilter(req, res);
	}
}
