<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Danh mục</title>
  <link rel="stylesheet" href="<c:url value='/css/app.css'/>">
</head>
<body>
  <div class="container">
    <div class="card">
      <div style="display:flex;align-items:center;justify-content:space-between">
        <h2 class="title">Danh mục của bạn</h2>
        <a class="btn" href="<c:url value='/category/add'/>">+ Thêm danh mục</a>
      </div>

      <c:choose>
        <c:when test="${empty cateList}">
          <p class="subtle">Chưa có danh mục nào.</p>
        </c:when>
        <c:otherwise>
          <table class="table">
            <thead>
              <tr><th>#</th><th>Icon</th><th>Tên</th><th>Thao tác</th></tr>
            </thead>
            <tbody>
              <c:forEach items="${cateList}" var="c" varStatus="i">
                <tr>
                  <td>${i.index + 1}</td>
                  <td>
                    <c:if test="${not empty c.icons}">
                      <img src="<c:url value='/uploads/${c.icons}'/>" width="40" style="border-radius:8px">
                    </c:if>
                  </td>
                  <td>${c.cateName}</td>
                  <td>
                    <a class="btn-ghost" href="<c:url value='/category/edit?id=${c.cateId}'/>">Sửa</a>
                    <a class="btn-ghost" href="<c:url value='/category/delete?id=${c.cateId}'/>"
                       onclick="return confirm('Xóa danh mục này?')">Xóa</a>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</body>
</html>
