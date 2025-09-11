<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row justify-content-center">
  <div class="col-md-5">
    <h2 class="mb-4">Forgot Password</h2>
    <form method="post" action="${pageContext.request.contextPath}/forgot-password">
      <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username" required>
      </div>
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>
      <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
      </c:if>
      <button type="submit" class="btn btn-warning w-100">Reset Password</button>
    </form>
    <div class="mt-3">
      <a href="${pageContext.request.contextPath}/login">Back to Login</a>
    </div>
  </div>
</div>