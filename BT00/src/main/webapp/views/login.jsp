<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
  request.setAttribute("pageTitle","Đăng nhập");
  request.setAttribute("active","login");
  request.setAttribute("contentPage","/views/partials/login_form.jsp");
%>
<jsp:forward page="/views/_layout.jsp"/>
