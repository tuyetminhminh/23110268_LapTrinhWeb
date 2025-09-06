<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp"%>
<c:set var="pageTitle" value="Admin - Quản lý Category" />
<c:set var="currentUser" value="${sessionScope.currentUser}" />
<!-- Phần chào mừng -->
<div class="container mt-2">
	<div class="alert alert-info text-center">Xin chào,
		${currentUser.fullname}!</div>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
</div>
<h3>Danh sách Category</h3>
<a href="${pageContext.request.contextPath}/admin/category/add"
	class="btn btn-primary mb-3">+ Thêm Category</a>

<table class="table table-bordered table-striped">
	<thead class="table-dark">
		<tr>
			<th>ID</th>
			<th>Tên</th>
			<th>Ảnh</th>
			<th>Người tạo</th>
			<th>Hành động</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="c" items="${listcate}">
			<tr>
				<td>${c.id}</td>
				<td>${c.categoryname}</td>
				<td>
                    <c:if test="${not empty c.images}">
                        <img src="${c.images}" alt="Category Image" style="max-width: 100px; max-height: 100px;">
                    </c:if>
                </td>
				<td>${c.user.username}</td>
				<td><a
					href="${pageContext.request.contextPath}/admin/category/edit?id=${c.id}"
					class="btn btn-sm btn-warning">Sửa</a>
					<form
						action="${pageContext.request.contextPath}/admin/category/delete"
						method="post" style="display: inline">
						<input type="hidden" name="id" value="${c.id}">
						<button class="btn btn-sm btn-danger"
							onclick="return confirm('Xóa category này?')">Xóa</button>
					</form></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<%@ include file="/views/layout/footer.jsp"%>
