<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="grid grid-2">
  <div class="card">
    <p class="kicker">Bắt đầu</p>
    <h2 class="title">Chào mừng đến LTW</h2>
    <p class="subtle">Đăng nhập hoặc tạo tài khoản để quản lý danh mục của bạn.</p>
    <p>
      <a class="btn" href="<c:url value='/login'/>">Đăng nhập</a>
      <a class="btn-ghost" href="<c:url value='/register'/>">Đăng ký</a>
    </p>
  </div>
</div>
