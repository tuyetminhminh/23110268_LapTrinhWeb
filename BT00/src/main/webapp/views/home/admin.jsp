<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="grid grid-2">
  <div class="card">
    <span class="badge">Admin</span>
    <h2 class="title">Bảng điều khiển</h2>
    <p class="subtle">Quản trị hệ thống, duyệt người dùng, thống kê…</p>
    <p>
      <a class="btn" href="<c:url value='/admin/users'/>">Quản lý Users</a>
      <a class="btn-ghost" href="<c:url value='/category/list'/>">Category của tôi</a>
    </p>
  </div>
  <div class="card">
    <h3 class="title">Danh mục cá nhân</h3>
    <jsp:include page="/views/home/user.jsp"/>
  </div>
</div>
