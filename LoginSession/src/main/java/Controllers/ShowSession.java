package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class ShowSession
 */
@WebServlet("/ShowSession")
public class ShowSession extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ShowSession() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// hiển thị session lên web
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String ten = "";
		// khởi tạo session
		HttpSession s = req.getSession();
		Object obj = s.getAttribute("ten"); // truy xuất dữ liệu từ session
		// kiểm tra đối tượng Object có null không
		if (obj != null) {
			ten = String.valueOf(obj); // ép kiểu về String
		} else {
			resp.sendRedirect("/HelloServlet/creatsession"); // nếu null thì chuyển về trang tạo session
		}
		int tuoi = (Integer) s.getAttribute("tuoi");// ép kiểu
		// Hiển thị session lên web
		out.println("Xin chào bạn: " + ten + " tuổi: " + tuoi);
		out.close();
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
