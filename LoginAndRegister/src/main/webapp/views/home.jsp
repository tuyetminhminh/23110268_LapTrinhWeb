<%-- views/home.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ltw.models.User, ltw.util.Constant" %>
<%
    User user = (User) session.getAttribute(Constant.SESSION_ACCOUNT);
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= user.getFullName() %>!</h2>
        <p>Role: <%= user.getRoleid() == 1 ? "Admin" : (user.getRoleid() == 2 ? "Manager" : "User") %></p>
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-secondary">Đăng xuất</a>
    </div>
</body>
</html>