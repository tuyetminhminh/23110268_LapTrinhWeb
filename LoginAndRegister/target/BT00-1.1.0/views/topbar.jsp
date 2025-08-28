<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>
</head>
<body>
	<h2>Tạo tài khoản mới</h2>
	<c:if test="${alert != null}">
		<div style="color: red">${alert}</div>
	</c:if>
	<form action="register" method="post">
		<label>Tài khoản</label><input type="text" name="username" /><br /> <label>Mật
			khẩu</label><input type="password" name="password" /><br /> <label>Email</label><input
			type="email" name="email" /><br /> <label>Họ tên</label><input
			type="text" name="fullname" /><br /> <label>Điện thoại</label><input
			type="text" name="phone" /><br />
		<button type="submit">Đăng ký</button>
	</form>
</body>
</html>