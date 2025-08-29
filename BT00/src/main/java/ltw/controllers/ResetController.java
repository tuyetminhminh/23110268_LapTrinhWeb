package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ltw.service.UserServiceImpl;

@WebServlet("/reset")
public class ResetController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String otp = req.getParameter("otp");
		String pass = req.getParameter("password");

		UserServiceImpl service = new UserServiceImpl();
		if (service.resetPassword(email, otp, pass)) {
			resp.sendRedirect(req.getContextPath() + "/login?success=reset");
		} else {
			req.setAttribute("error", "OTP không hợp lệ hoặc đã hết hạn!");
			req.getRequestDispatcher("/views/reset.jsp").forward(req, resp);
		}
	}
}
