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

@WebServlet("/category/list")
public class ListController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CategoryService service = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws ServletException, IOException {
		    User u = (User) req.getSession().getAttribute("currentUser");
		    if (u == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

		    req.setAttribute("cateList", service.getAllByUser(u.getId()));

		    // layout + trang con
		    req.setAttribute("pageTitle", "Danh mục");
		    req.setAttribute("active", "category");
		    req.setAttribute("contentPage", "/views/category/list.jsp");
		    req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
		  }

}
