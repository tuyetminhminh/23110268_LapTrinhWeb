<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card" style="max-width:640px;margin:40px auto">
  <h2 class="title">Sửa danh mục (Admin)</h2>
  <c:if test="${not empty error}"><p style="color:#dc2626">${error}</p></c:if>

  <form method="post" action="<c:url value='/admin/user/category/edit'/>"
        enctype="multipart/form-data" class="grid">
    <input type="hidden" name="userId" value="${targetUserId}">
    <input type="hidden" name="id" value="${category.cateId}">

    <div>
      <label>Tên danh mục</label>
      <input class="input" type="text" name="name" value="${category.cateName}" required>
    </div>

    <div>
      <label>Icon hiện tại</label><br>
      <c:if test="${not empty category.icons}">
        <img src="<c:url value='/uploads/${category.icons}'/>" width="80" style="border-radius:8px">
      </c:if>
    </div>

    <div>
      <label>Đổi icon (nếu muốn)</label>
      <input class="input" type="file" name="iconFile" accept="image/*">
    </div>

    <div>
      <button class="btn" type="submit">Lưu</button>
      <a class="btn-ghost" href="<c:url value='/admin/user/categories?userId=${targetUserId}'/>">Hủy</a>
    </div>
  </form>
</div>
