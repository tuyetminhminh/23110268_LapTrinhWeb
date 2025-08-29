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
import ltw.models.User;
import ltw.service.CategoryService;
import ltw.service.CategoryServiceImpl;

@WebServlet("/category/add")
@MultipartConfig( // có thể set thêm size nếu muốn
		fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 5L * 1024 * 1024, // 5MB
		maxRequestSize = 10L * 1024 * 1024 // 10MB
)
public class AddController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CategoryService service = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getSession().getAttribute("currentUser");
	    if (u == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

	    req.setAttribute("pageTitle", "Thêm danh mục");
	    req.setAttribute("active", "category");
	    req.setAttribute("contentPage", "/views/category/add.jsp");
	    req.getRequestDispatcher("/views/_layout.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getSession().getAttribute("currentUser");
		if (u == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}

		req.setCharacterEncoding("UTF-8");
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
		c.setIcons(savedName); // có thể null nếu không chọn file
		c.setUserId(u.getId());
		service.insert(c);

		resp.sendRedirect(req.getContextPath() + "/category/list");
	}

	// Lấy phần đuôi mở rộng từ tên file gốc
	private static String guessExt(String filename) {
		if (filename == null)
			return "";
		int dot = filename.lastIndexOf('.');
		return (dot >= 0) ? filename.substring(dot).toLowerCase() : "";
	}
}
