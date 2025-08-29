package ltw.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ltw.models.User;
import ltw.service.UserServiceImpl;
import ltw.util.Constant;
import ltw.service.UserService;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession(false);
	    if (session != null && session.getAttribute(Constant.SESSION_ACCOUNT) != null) {
	      resp.sendRedirect(req.getContextPath()+ "/waiting"); 
	      return;
	    }
	    // Check cookie "username" (remember me)
	    Cookie[] cookies = req.getCookies();
	    if (cookies != null) {
	      for (Cookie c : cookies) {
	        if (Constant.COOKIE_REMEMBER.equals(c.getName())) {
	          session = req.getSession(true);
	          session.setAttribute(Constant.SESSION_USERNAME, c.getValue());
	          resp.sendRedirect(req.getContextPath()+ "/waiting"); 
	          return;
	        }
	      }
	    }
	    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	  }

	  @Override
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");
	    boolean isRememberMe = "on".equals(req.getParameter("remember"));

	    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	      req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng"); 
	      req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	      return;
	    }

	    UserService service = new UserServiceImpl();
	    User user = service.login(username, password);

	    if (user != null) {
	      HttpSession session = req.getSession(true);
	      session.setAttribute(Constant.SESSION_ACCOUNT, user);
	      if (isRememberMe) saveRememberMe(resp, username); 
	      resp.sendRedirect(req.getContextPath()+"/waiting");
	    } else {
	      req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng"); 
	      req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
	    }
	  }

	  private void saveRememberMe(HttpServletResponse response, String username) {
	    Cookie cookie = new Cookie(Constant.COOKIE_REMEMBER, username);
	    cookie.setMaxAge(30*60); // 30 phút
	    response.addCookie(cookie);
	  }
}
