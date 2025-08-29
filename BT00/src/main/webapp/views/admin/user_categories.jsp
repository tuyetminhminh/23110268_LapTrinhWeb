<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="card">
	<h2>Danh mục của: ${targetUser.fullName} (${targetUser.userName})</h2>
	<p>
		<a class="btn" href="<c:url value='/admin/users'/>">⬅ Quay lại
			danh sách User</a>
	</p>

	<c:choose>
		<c:when test="${empty cateList}">
			<p>Người dùng này chưa có danh mục nào.</p>
			<p>
				<a class="btn"
					href="<c:url value='/admin/user/category/add?userId=${targetUser.id}'/>">
					+ Thêm danh mục </a> <a class="btn-ghost"
					href="<c:url value='/admin/users'/>">⬅ Quay lại</a>
			</p>

		</c:when>
		<c:otherwise>
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th>Icon</th>
						<th>Tên</th>
						<th>Thao tác</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${cateList}" var="c" varStatus="i">
						<tr>
							<td>${i.index+1}</td>
							<td><c:if test="${not empty c.icons}">
									<img src="<c:url value='/uploads/${c.icons}'/>" width="40"
										height="40">
								</c:if></td>
							<td>${c.cateName}</td>
							<td><a class="btn-ghost"
								href="<c:url value='/admin/user/category/edit?id=${c.cateId}&userId=${targetUser.id}'/>">Sửa</a>
								<a class="btn-ghost"
								href="<c:url value='/admin/user/category/delete?id=${c.cateId}&userId=${targetUser.id}'/>"
								onclick="return confirm('Xóa danh mục này?')">Xóa</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</div>
