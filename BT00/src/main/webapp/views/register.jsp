<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Đăng ký</title>
  <link rel="stylesheet" href="<c:url value='/css/app.css'/>">
</head>
<body>
  <div class="container">
    <div class="card" style="max-width:640px;margin:40px auto">
      <h2 class="title">Tạo tài khoản</h2>

      <c:if test="${not empty alert}">
        <p style="color:#dc2626">${alert}</p>
      </c:if>

      <form action="<c:url value='/register'/>" method="post" class="grid">
        <div>
          <label>Tài khoản</label>
          <input class="input" type="text" name="username" required>
        </div>
        <div>
          <label>Mật khẩu</label>
          <input class="input" type="password" name="password" required>
        </div>
        <div class="grid grid-2">
          <div>
            <label>Họ tên</label>
            <input class="input" type="text" name="fullname">
          </div>
          <div>
            <label>Điện thoại</label>
            <input class="input" type="text" name="phone">
          </div>
        </div>
        <div>
          <label>Email</label>
          <input class="input" type="email" name="email" required>
        </div>
        <div>
          <button class="btn" type="submit">Đăng ký</button>
          <a class="btn-ghost" href="<c:url value='/login'/>">Đã có tài khoản?</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
