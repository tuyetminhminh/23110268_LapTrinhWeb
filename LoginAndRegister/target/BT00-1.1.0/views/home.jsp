<%-- views/admin-home.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="ltw.models.User, ltw.util.Constant" %>
<%
    User user = (User) session.getAttribute(Constant.SESSION_ACCOUNT);
    if (user == null || user.getRoleid() != 1) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h2>Welcome Admin, <%= user.getFullName() %>!</h2>
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-secondary">Đăng xuất</a>
    </div>
</body>
</html>