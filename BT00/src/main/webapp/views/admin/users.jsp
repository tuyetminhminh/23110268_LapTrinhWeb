<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card">
  <h2 class="title">Quản lý người dùng</h2>
  <table class="table">
    <thead>
      <tr>
        <th>#</th><th>Username</th><th>Họ tên</th><th>Email</th><th>Phone</th><th>Role</th><th>Thao tác</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${users}" var="u" varStatus="st">
        <tr>
          <td>${st.index + 1}</td>
          <td>${u.userName}</td>
          <td>${u.fullName}</td>
          <td>${u.email}</td>
          <td>${u.phone}</td>
          <td>
            <c:choose>
              <c:when test="${u.roleid == 1}">Admin</c:when>
              <c:when test="${u.roleid == 2}">Manager</c:when>
              <c:otherwise>User</c:otherwise>
            </c:choose>
          </td>
          <td>
            <a class="btn-ghost" href="<c:url value='/admin/user/categories?userId=${u.id}'/>">
              Quản lý danh mục
            </a>
            <!-- có thể thêm: Sửa thông tin user, Đổi role, Khóa, Xóa -->
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
