<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="u" value="${user != null ? user : sessionScope.currentUser}"/>

<h1 class="h4 mb-4">Hồ sơ cá nhân</h1>

<c:if test="${not empty param.success}">
  <div class="alert alert-success">Cập nhật hồ sơ thành công.</div>
</c:if>
<c:if test="${not empty error}">
  <div class="alert alert-danger">${error}</div>
</c:if>

<div class="row">
  <div class="col-md-4 mb-4">
    <div class="card">
      <div class="card-body text-center">
        <img class="img-fluid rounded mb-3"
             src="<c:url value='/images/${u.image}'/>"
             onerror="this.src='<c:url value='/assets/img/avatar-placeholder.png'/>'"
             alt="Avatar">
        <p class="text-muted small mb-0">${u.username}</p>
        <p class="mb-0">${u.fullname}</p>
      </div>
    </div>
  </div>

  <div class="col-md-8">
    <form action="<c:url value='/profile'/>" method="post" enctype="multipart/form-data" class="card">
      <div class="card-body">
        <div class="mb-3">
          <label class="form-label">Fullname</label>
          <input name="fullname" class="form-control" value="${u.fullname}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Phone</label>
          <input name="phone" class="form-control" value="${u.phone}" placeholder="09xxxxxxxx" pattern="[0-9]{9,11}">
          <div class="form-text">Chỉ nhập 9-11 chữ số.</div>
        </div>
        <div class="mb-3">
          <label class="form-label">Ảnh đại diện</label>
          <input name="image" type="file" accept="image/*" class="form-control">
          <div class="form-text">Tối đa 5MB. Bỏ trống nếu giữ ảnh hiện tại.</div>
        </div>
      </div>
      <div class="card-footer text-end">
        <button class="btn btn-primary">Lưu thay đổi</button>
      </div>
    </form>
  </div>
</div>
