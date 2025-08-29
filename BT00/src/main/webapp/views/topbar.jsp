<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang mở đầu</title>
<style>
    body {
        font-family: Arial, sans-serif;
        text-align: center;
        margin-top: 100px;
    }
    h1 {
        color: #333;
    }
    .btn {
        display: inline-block;
        margin: 10px;
        padding: 12px 24px;
        font-size: 16px;
        text-decoration: none;
        border-radius: 6px;
        background-color: #4CAF50;
        color: white;
    }
    .btn:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>
    <h1>Chào mừng đến với Website</h1>
    <p>Vui lòng chọn</p>
    
    <a href="<c:url value='/views/login.jsp'/>" class="btn">Đăng nhập</a>
    <a href="<c:url value='/views/register.jsp'/>" class="btn">Đăng ký</a>
    <a href="<c:url value='/views/forgot.jsp'/>">Quên mật khẩu?</a>
    
</body>
</html>
