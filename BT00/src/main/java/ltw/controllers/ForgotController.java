package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltw.service.UserService;
import ltw.service.UserServiceImpl;

@WebServlet("/forgot")
public class ForgotController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		UserService service = new UserServiceImpl();
		if (service.sendOTP(email)) {
			req.setAttribute("msg", "OTP đã gửi qua email!");
			req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
		} else {
			req.setAttribute("error", "Email không tồn tại!");
			req.getRequestDispatcher("/views/forgot.jsp").forward(req, resp);
		}
	}

}
