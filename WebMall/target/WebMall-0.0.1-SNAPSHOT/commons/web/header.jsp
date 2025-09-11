<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar navbar-expand-lg bg-body-tertiary border-bottom">
  <div class="container">
    <a class="navbar-brand fw-bold" href="<c:url value='/'/>">WebMall</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="mainNav" class="collapse navbar-collapse">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="<c:url value='/home'/>">Home</a></li>
        <c:choose>
          <c:when test="${not empty sessionScope.currentUser}">
            <li class="nav-item"><a class="nav-link" href="<c:url value='/profile'/>">Profile</a></li>
            <c:if test="${sessionScope.currentUser.role eq 'admin'}">
              <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/dashboard'/>">Admin</a></li>
            </c:if>
            <li class="nav-item"><a class="nav-link" href="<c:url value='/logout'/>">Logout</a></li>
          </c:when>
          <c:otherwise>
            <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Login</a></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value='/register'/>">Register</a></li>
            <li class="nav-item"><a class="nav-link" href="<c:url value='/forgot-password'/>">Forgot</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
</nav>
