<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ include file="/views/layout/header.jsp"%>
<c:set var="pageTitle" value="Chi tiết Category" />
<h3>Chi tiết Category</h3>
<div class="card">
	<div class="card-body">
		<p>
			<b>ID:</b> ${category.id}
		</p>
		<p>
			<b>Tên:</b> ${category.categoryname}
		</p>
		<p>
			<b>Ảnh:</b> ${category.images}
		</p>
		<p>
			<b>Owner:</b> ${category.user.username}
		</p>
	</div>
</div>
<a class="btn btn-secondary mt-3" href="javascript:history.back()">Quay
	lại</a>
<%@ include file="/views/layout/footer.jsp"%>
