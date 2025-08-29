<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="<c:url value='/css/app.css'/>">
<nav class="navbar">
	<div class="navbar-inner">
		<div class="brand">
			<span class="dot"></span> Bài thực hành số 3
		</div>
		<div class="nav-links">
			<a href="<c:url value='/home'/>">Trang chủ</a>
			<c:choose>
				<c:when test="${not empty currentUser}">
					<a href="<c:url value='/category/list'/>">Category</a>
					<a class="btn-ghost" href="<c:url value='/logout'/>">Đăng xuất</a>
				</c:when>
				<c:when test="${not empty currentUser}">
					<a href="<c:url value='/category/list'/>">Category</a>
					<c:if test="${currentUser.roleid == 1}">
						<a href="<c:url value='/admin/users'/>">Quản lý User</a>
					</c:if>
					<a class="btn-ghost" href="<c:url value='/logout'/>">Đăng xuất</a>
				</c:when>
				<c:otherwise>
					<a href="<c:url value='/login'/>">Đăng nhập</a>
					<a class="btn" href="<c:url value='/register'/>">Đăng ký</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</nav>

<main class="container">
	<jsp:include page="${contentPage}" />
</main>
