<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  request.setAttribute("pageTitle","Danh mục");
  request.setAttribute("active","category");
  request.setAttribute("contentPage","/views/category/list.jsp");
%>
<jsp:forward page="/views/_layout.jsp"/>
