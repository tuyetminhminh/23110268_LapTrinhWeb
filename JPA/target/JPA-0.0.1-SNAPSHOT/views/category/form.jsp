<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp" %>

<c:set var="pageTitle" value="${empty category ? 'Thêm Category' : 'Sửa Category'}" />
<c:set var="currentUser" value="${sessionScope.currentUser}" />

<div class="row justify-content-center">
    <div class="col-md-6">
        <h3 class="text-center mb-4">${pageTitle}</h3>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <c:set var="rolePrefix" value="${currentUser.roleId == 3 ? 'admin' : currentUser.roleId == 2 ? 'manager' : 'user'}" />
        <c:set var="actionPath" value="${empty category ? '/category/add' : '/category/edit'}" />
        <c:if test="${empty error or (not empty category and (currentUser.roleId == 3 or category.user.id == currentUser.id))}">
            <form method="post" action="${pageContext.request.contextPath}/${rolePrefix}${actionPath}" enctype="multipart/form-data">
                <c:if test="${not empty category}">
                    <input type="hidden" name="id" value="${category.id}" />
                </c:if>
                
                <div class="mb-3">
                    <label for="categoryname" class="form-label">Tên:</label>
                    <input type="text" class="form-control" id="categoryname" name="categoryname" 
                           value="${category.categoryname}" required>
                </div>
                
                <div class="mb-3">
                    <label for="images" class="form-label">Ảnh:</label>
                    <input type="file" class="form-control" id="images" name="images" accept="image/*">
                    <c:if test="${not empty category.images}">
                        <div class="mt-2">
                            <img src="${category.images}" alt="Current Image" style="max-width: 200px; max-height: 200px;">
                        </div>
                    </c:if>
                </div>
                
                <c:if test="${currentUser.roleId == 3 or currentUser.roleId == 2}">
                    <div class="mb-3">
                        <label for="userId" class="form-label">Chọn User sở hữu:</label>
                        <select class="form-select" id="userId" name="userId" required>
                            <option value="">-- Chọn User --</option>
                            <c:forEach var="user" items="${userList}">
                                <option value="${user.id}" ${user.id == currentUser.id ? 'selected' : ''}>${user.username}</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                
                <button type="submit" class="btn btn-primary w-100">Lưu</button>
            </form>
        </c:if>
    </div>
</div>

<%@ include file="/views/layout/footer.jsp" %>