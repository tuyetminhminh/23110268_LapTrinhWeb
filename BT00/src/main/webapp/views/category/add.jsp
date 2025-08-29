<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Thêm danh mục</title>
  <link rel="stylesheet" href="<c:url value='/css/app.css'/>">
</head>
<body>
  <div class="container">
    <div class="card" style="max-width:640px;margin:40px auto">
      <h2 class="title">Thêm danh mục</h2>

      <c:if test="${not empty error}">
        <p style="color:#dc2626">${error}</p>
      </c:if>

      <form method="post" action="<c:url value='/category/add'/>"
            enctype="multipart/form-data" class="grid">
        <div>
          <label>Tên danh mục</label>
          <input class="input" type="text" name="name" required>
        </div>
        <div>
          <label>Icon (chọn ảnh)</label>
          <input class="input" type="file" name="iconFile" accept="image/*">
        </div>
        <div>
          <button class="btn" type="submit">Thêm</button>
          <a class="btn-ghost" href="<c:url value='/category/list'/>">Hủy</a>
        </div>
      </form>
    </div>
  </div>
</body>
</html>
