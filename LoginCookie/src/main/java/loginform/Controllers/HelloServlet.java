package loginform.Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet(urlPatterns = { "/hello", "/xin-chao" })
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    resp.setContentType("text/html;charset=UTF-8");
	    PrintWriter out = resp.getWriter();

	    String name = "";
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	        for (Cookie c : cookies) {
	            if ("username".equals(c.getName())) {
	                name = c.getValue();
	                break;
	            }
	        }
	    }

	    if (name.equals("")) {
	        // Nếu chưa đăng nhập thì quay lại login.html
	        resp.sendRedirect(req.getContextPath() + "/login.html");
	        return;
	    }

	    // Nếu đã đăng nhập thì hiển thị lời chào
	    out.println("<h2>Xin chào " + name + "</h2>");
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
