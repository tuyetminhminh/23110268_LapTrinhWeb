<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp"%>

<c:set var="pageTitle" value="Danh sách Category" />
<c:set var="currentUser" value="${sessionScope.currentUser}" />

<div class="container mt-2">
    <div class="alert alert-info text-center">
        Xin chào, ${currentUser.fullname}!
    </div>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
</div>

<h3>Danh sách Category</h3>
<a href="${pageContext.request.contextPath}/${currentUser.roleId == 3 ? 'admin' : currentUser.roleId == 2 ? 'manager' : 'user'}/category/add" class="btn btn-primary mb-3">+ Thêm Category</a>

<c:if test="${currentUser.roleId == 2 && not empty userList}">
    <form action="${pageContext.request.contextPath}/manager/home" method="get" class="mb-3">
        <div class="input-group w-25">
            <select name="userId" class="form-select" onchange="this.form.submit()">
                <option value="">-- Chọn User --</option>
                <c:forEach var="user" items="${userList}">
                    <option value="${user.id}" ${param.userId == user.id ? 'selected' : ''}>${user.username}</option>
                </c:forEach>
            </select>
        </div>
    </form>
</c:if>

<table class="table table-bordered table-striped">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Tên</th>
            <th>Ảnh</th>
            <th>Chủ sở hữu</th>
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
                <td>
                    <a href="${pageContext.request.contextPath}/${currentUser.roleId == 3 ? 'admin' : currentUser.roleId == 2 ? 'manager' : 'user'}/category/detail?id=${c.id}" class="btn btn-sm btn-info">Xem</a>
                    <c:if test="${currentUser.roleId == 3}">
                        <a href="${pageContext.request.contextPath}/admin/category/edit?id=${c.id}" class="btn btn-sm btn-warning">Sửa</a>
                    </c:if>
                    <c:if test="${currentUser.roleId == 2 and c.user.roleId != 3}">
                        <a href="${pageContext.request.contextPath}/manager/category/edit?id=${c.id}" class="btn btn-sm btn-warning">Sửa</a>
                    </c:if>
                    <c:if test="${currentUser.roleId == 1 and c.user.id == currentUser.id}">
                        <a href="${pageContext.request.contextPath}/user/category/edit?id=${c.id}" class="btn btn-sm btn-warning">Sửa</a>
                    </c:if>
                    <c:if test="${currentUser.roleId == 3 or c.user.id == currentUser.id}">
                        <form action="${pageContext.request.contextPath}/${currentUser.roleId == 3 ? 'admin' : currentUser.roleId == 2 ? 'manager' : 'user'}/category/edit" method="post" style="display: inline">
                            <input type="hidden" name="id" value="${c.id}">
                            <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Xóa category này?')">Xóa</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="/views/layout/footer.jsp" %>