<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="grid grid-2">
  <div class="card">
    <h2 class="title">Đăng nhập</h2>
    <c:if test="${not empty error}"><p style="color:#dc2626">${error}</p></c:if>
    <form action="<c:url value='/login'/>" method="post" class="grid">
      <input class="input" type="text" name="username" placeholder="Tài khoản" required>
      <input class="input" type="password" name="password" placeholder="Mật khẩu" required>
      <button class="btn" type="submit">Đăng nhập</button>
    </form>
    <p class="subtle">Quên mật khẩu?
      <a href="<c:url value='/forgot'/>">Lấy lại bằng OTP</a>
    </p>
  </div>
  <div class="card">
    <p class="kicker">Chưa có tài khoản?</p>
    <a class="btn-ghost" href="<c:url value='/register'/>">Tạo tài khoản</a>
  </div>
</div>
