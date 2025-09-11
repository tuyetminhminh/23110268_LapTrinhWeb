package web.controllers;

import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/profile")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5MB
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		User user = (User) (session != null ? session.getAttribute("currentUser") : null);
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		req.setAttribute("user", user);
		req.setAttribute("success", req.getParameter("success"));
		req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession(false);
		User user = (User) (session != null ? session.getAttribute("currentUser") : null);
		if (user == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		String fullname = req.getParameter("fullname");
		String phone = req.getParameter("phone");
		Part imagePart = req.getPart("image");

		String imageName = user.getImage();

		if (imagePart != null && imagePart.getSize() > 0) {
			// Kiểm tra MIME cơ bản
			String contentType = imagePart.getContentType();
			if (contentType == null || !contentType.startsWith("image/")) {
				req.setAttribute("error", "File ảnh không hợp lệ.");
				req.setAttribute("user", user);
				req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
				return;
			}

			// Thư mục /images trong webapp (Tomcat exploded)
			String imagesDir = req.getServletContext().getRealPath("/images");
			if (imagesDir == null) {
				// fallback hiếm (nếu không exploded) -> tạo tạm trong temp để tránh crash
				imagesDir = System.getProperty("java.io.tmpdir") + File.separator + "images";
			}
			Files.createDirectories(Paths.get(imagesDir));

			// Lấy tên file gốc và tạo tên mới tránh trùng
			String submitted = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
			String ext = "";
			int dot = submitted.lastIndexOf('.');
			if (dot >= 0)
				ext = submitted.substring(dot).toLowerCase();

			String newFileName = user.getUsername() + "_" + System.currentTimeMillis() + ext;

			File file = new File(imagesDir, newFileName);
			imagePart.write(file.getAbsolutePath());

			// (tuỳ chọn) xoá ảnh cũ nếu có và khác ảnh mặc định
			if (imageName != null && !imageName.isBlank() && !imageName.equals(newFileName)) {
				File old = new File(imagesDir, imageName);
				if (old.exists())
					try {
						old.delete();
					} catch (Exception ignore) {
					}
			}

			imageName = newFileName;
			user.setFullname(fullname);
			user.setPhone(phone);
			user.setImage(imageName);
			UserDao userDao = new UserDaoImpl();
			userDao.update(user);
			session.setAttribute("currentUser", user);
			resp.sendRedirect(req.getContextPath() + "/profile?success=1");
		}
	}
}