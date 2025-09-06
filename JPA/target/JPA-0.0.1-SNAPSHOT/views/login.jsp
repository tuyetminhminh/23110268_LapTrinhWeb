<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/layout/header.jsp" %>
<c:set var="pageTitle" value="Đăng nhập" />
<div class="row justify-content-center">
  <div class="col-md-4">
    <h3 class="text-center">Đăng nhập</h3>
    <c:if test="${not empty error}">
      <div class="alert alert-danger">${error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="mb-3">
        <label class="form-label">Tên đăng nhập</label>
        <input type="text" name="username" class="form-control" required>
      </div>
      <div class="mb-3">
        <label class="form-label">Mật khẩu</label>
        <input type="password" name="password" class="form-control" required>
      </div>
      <div class="form-check mb-3">
        <input type="checkbox" class="form-check-input" name="remember" id="remember">
        <label for="remember" class="form-check-label">Ghi nhớ đăng nhập</label>
      </div>
      <button type="submit" class="btn btn-primary w-100">Đăng nhập</button>
      <p class="text-center mt-3">
        <a href="${pageContext.request.contextPath}/register">Đăng ký</a> | 
        <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
      </p>
    </form>
  </div>
</div>
<%@ include file="/views/layout/footer.jsp" %>
