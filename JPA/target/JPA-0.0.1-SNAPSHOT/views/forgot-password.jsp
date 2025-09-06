<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp"%>
<c:set var="pageTitle" value="Quên mật khẩu" />

<div class="row justify-content-center">
	<div class="col-md-5">
		<h3 class="text-center mb-4">Quên mật khẩu</h3>

		<!-- Hiển thị thông báo lỗi -->
		<c:if test="${not empty error}">
			<div class="alert alert-danger">${error}</div>
		</c:if>

		<!-- Hiển thị thông báo thành công -->
		<c:if test="${not empty message}">
			<div class="alert alert-success">${message}</div>
		</c:if>

		<form method="post"
			action="${pageContext.request.contextPath}/forgot-password">
			<div class="mb-3">
				<label class="form-label">Nhập email đã đăng ký</label> <input
					type="email" name="email" class="form-control"
					placeholder="Email của bạn" required />
			</div>
			<button type="submit" class="btn btn-primary w-100">Lấy lại
				mật khẩu</button>
		</form>

		<div class="text-center mt-3">
			<a href="${pageContext.request.contextPath}/login">← Quay về
				trang đăng nhập</a>
		</div>
	</div>
</div>

<%@ include file="/views/layout/footer.jsp"%>
