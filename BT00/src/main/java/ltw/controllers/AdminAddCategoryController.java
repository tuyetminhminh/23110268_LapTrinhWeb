package ltw.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import ltw.models.Category;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/admin/user/category/add")
@MultipartConfig
public class AdminAddCategoryController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = Integer.parseInt(req.getParameter("userId"));
		String name = req.getParameter("name");

		// Lấy file upload
		Part filePart = req.getPart("iconFile");
		String savedName = null;
		if (filePart != null && filePart.getSize() > 0) {
			// Kiểm tra MIME cơ bản
			String contentType = filePart.getContentType();
			if (contentType == null || !contentType.startsWith("image/")) {
				req.setAttribute("error", "Chỉ cho phép upload ảnh.");
				req.getRequestDispatcher("/views/category/add.jsp").forward(req, resp);
				return;
			}

			// Tên file an toàn/unique
			String ext = guessExt(filePart.getSubmittedFileName()); // .png/.jpg...
			savedName = UUID.randomUUID().toString().replaceAll("-", "") + ext;

			// Đường dẫn thư mục /uploads thực trên máy
			String uploadDir = getServletContext().getRealPath("/uploads");
			new File(uploadDir).mkdirs();

			// Lưu file
			File dest = new File(uploadDir, savedName);
			try (var in = filePart.getInputStream()) {
				Files.copy(in, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
		}

		Category c = new Category();
		c.setCateName(name);
		c.setIcons(savedName);
		c.setUserId(userId);

		cateService.insert(c);

		resp.sendRedirect(req.getContextPath() + "/admin/user/categories?userId=" + userId);
	}

	// Lấy phần đuôi mở rộng từ tên file gốc
	private static String guessExt(String filename) {
		if (filename == null)
			return "";
		int dot = filename.lastIndexOf('.');
		return (dot >= 0) ? filename.substring(dot).toLowerCase() : "";
	}
}
