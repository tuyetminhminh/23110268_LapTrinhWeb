<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${sitemesh.page.title}</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
${sitemesh.page.head}
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
		<div class="container">
			<a class="navbar-brand" href="/">WebMall</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<c:choose>
						<c:when test="${not empty sessionScope.currentUser}">
							<li class="nav-item"><a class="nav-link" href="/profile">Profile</a></li>
							<c:if test="${sessionScope.currentUser.role eq 'admin'}">
								<li class="nav-item"><a class="nav-link"
									href="/admin/dashboard">Admin</a></li>
							</c:if>
							<li class="nav-item"><a class="nav-link" href="/logout">Logout</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item"><a class="nav-link" href="/login">Login</a></li>
							<li class="nav-item"><a class="nav-link" href="/register">Register</a></li>
							<li class="nav-item"><a class="nav-link"
								href="/forgot-password">Forgot Password</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">${sitemesh.page.body}</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>