<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Quên mật khẩu</title>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <h2>Quên mật khẩu</h2>
    
    <!-- Hiển thị thông báo lỗi -->
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    
    <!-- Hiển thị thông báo thành công -->
    <c:if test="${not empty msg}">
        <p style="color:green">${msg}</p>
    </c:if>

    <form action="<c:url value='/forgot'/>" method="post">
        <label for="email">Nhập email của bạn:</label><br>
        <input type="email" id="email" name="email" required placeholder="yourmail@example.com"><br><br>
        
        <button type="submit">Gửi OTP</button>
    </form>
    
    <p><a href="<c:url value='/login.jsp'/>">Quay lại đăng nhập</a></p>
</body>
</html>
