<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đặt lại mật khẩu</title>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <h2>Đặt lại mật khẩu</h2>
    
    <!-- Thông báo -->
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <c:if test="${not empty msg}">
        <p style="color:green">${msg}</p>
    </c:if>
    
    <form action="<c:url value='/reset'/>" method="post">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required value="${param.email}"><br><br>
        
        <label for="otp">OTP:</label><br>
        <input type="text" id="otp" name="otp" required placeholder="Nhập mã OTP"><br><br>
        
        <label for="password">Mật khẩu mới:</label><br>
        <input type="password" id="password" name="password" required><br><br>
        
        <button type="submit">Đặt lại mật khẩu</button>
    </form>
    
    <p><a href="<c:url value='/login.jsp'/>">Quay lại đăng nhập</a></p>
</body>
</html>
