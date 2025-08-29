<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="grid grid-2">
  <div class="card">
    <span class="badge">Manager</span>
    <h2 class="title">Quản lý nội dung</h2>
    <p class="subtle">Duyệt Category/ thống kê theo nhóm.</p>
    <p>
      <a class="btn" href="<c:url value='/manager/reports'/>">Báo cáo</a>
      <a class="btn-ghost" href="<c:url value='/category/list'/>">Category của tôi</a>
    </p>
  </div>
  <div class="card">
    <h3 class="title">Danh mục cá nhân</h3>
    <jsp:include page="/views/home/user.jsp"/>
  </div>
</div>
    