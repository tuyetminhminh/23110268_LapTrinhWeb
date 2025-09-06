<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp"%>
<c:set var="pageTitle" value="Đăng ký" />
<div class="row justify-content-center">
	<div class="col-md-5">
		<h3 class="text-center">Đăng ký tài khoản</h3>
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>
		<form method="post"
			action="${pageContext.request.contextPath}/register">
			<div class="mb-3">
				<label class="form-label">Tên đăng nhập</label> <input type="text"
					name="username" class="form-control" required>
			</div>
			<div class="mb-3">
				<label class="form-label">Mật khẩu</label> <input type="password"
					name="password" class="form-control" required>
			</div>
			<div class="mb-3">
				<label class="form-label">Họ tên</label> <input type="text"
					name="fullname" class="form-control">
			</div>
			<div class="mb-3">
				<label class="form-label">Email</label> <input type="email"
					name="email" class="form-control">
			</div>
			<button type="submit" class="btn btn-success w-100">Đăng ký</button>
		</form>
	</div>
</div>
<%@ include file="/views/layout/footer.jsp"%>
