package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltw.models.User;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/category/delete")
public class DeleteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CategoryService service = new CategoryServiceImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getSession().getAttribute("currentUser");
		if (u == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			// dùng đúng getter theo model của bạn: getUserId() hoặc getId()
			int userId = (u.getId() != 0) ? u.getId() : u.getId();
			service.delete(id, userId);
			resp.sendRedirect(req.getContextPath() + "/category/list");
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category id");
		}
	}

}
