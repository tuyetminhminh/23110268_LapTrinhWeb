<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<div class="row justify-content-center">
  <div class="col-md-4">
    <h2 class="mb-4">Login</h2>
    <form method="post" action="${pageContext.request.contextPath}/login">
      <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username" required value="${not empty rememberedUsername ? rememberedUsername : ''}">
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password" required>
      </div>
      <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="remember" name="remember" 
          ${not empty rememberedUsername ? 'checked' : ''}>
        <label class="form-check-label" for="remember">Remember Me</label>
      </div>
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>
      <button type="submit" class="btn btn-primary w-100">Login</button>
    </form>
    <div class="mt-3">
      <a href="${pageContext.request.contextPath}/register">Register</a> |
      <a href="${pageContext.request.contextPath}/forgot-password">Forgot Password?</a>
    </div>
    <c:if test="${not empty success}">
      <div class="alert alert-success mt-3">${success}</div>
    </c:if>
  </div>
</div>