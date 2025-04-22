<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="app-header navbar navbar-expand bg-body" style="height: 70px">
	<!--begin::Container-->
	<div class="container-fluid">
		<!--begin::Start Navbar Links-->

		<ul class="navbar-nav">
			<li class="nav-item d-none d-md-block">
				<a href="/devmap/index.do">
				<img src="/devmap/images/main/logo3.png" style="width: 150px"></a>
			</li>
		</ul>

		<!-- 로그인한 사용자만 메뉴 표시 -->
		<c:if test="${not empty userSeq}">
			<ul class="navbar-nav" style="font-size: 20px;">
				<li class="nav-item d-none d-md-block"><a
					href="/devmap/devtest.do" class="nav-link"
					style="height: 70px; display: flex; align-items: center;">RoadMap</a>
				</li>
				<li class="nav-item d-none d-md-block"><a
					href="/devmap/test/codetest/codetest.do" class="nav-link"
					style="height: 70px; display: flex; align-items: center;">CodeTest</a>
				</li>
				<li class="nav-item d-none d-md-block"><a
					href="/devmap/test/quiz/quiz.do" class="nav-link"
					style="height: 70px; display: flex; align-items: center;">Quiz</a>
				</li>
			</ul>
		</c:if>
		<!--end::Start Navbar Links-->
		<!--begin::End Navbar Links-->
		<ul class="navbar-nav ms-auto" style="font-size: 20px">
			<!-- 로그인하지 않은 사용자를 위한 로그인/회원가입 링크 -->
			<c:if test="${empty userSeq}">
				<li class="nav-item d-none d-md-block">
					<a href="${pageContext.request.contextPath}/user/login" class="nav-link">로그인</a>
				</li>
				<li class="nav-item d-none d-md-block">
					<a href="${pageContext.request.contextPath}/user/register" class="nav-link">회원가입</a>
				</li>
			</c:if>
			
			<!-- 로그인한 사용자만 알림과 사용자 메뉴 표시 -->
			<c:if test="${not empty userSeq}">
				<li class="nav-item dropdown" style="position: relative;"><a
					class="nav-link" data-bs-toggle="dropdown" href="#"
					style="height: 70px; display: flex; align-items: center; position: relative;">
						<i class="bi bi-bell-fill"></i> <span
						class="badge text-bg-warning"
						style="font-size: 12px; padding: 4px 6px; border-radius: 50%; position: absolute; top: 16px; right: 0;">${notiCount}</span>
				</a>
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-end">
						<span class="dropdown-item dropdown-header">${notification}</span>
						<c:forEach var="noti" items="${notiList}">
							<div class="dropdown-divider"></div>
							<a href="#" class="dropdown-item">${noti}</a>
						</c:forEach>
					</div></li>

				<li class="nav-item dropdown user-menu"><a href="#!"
					class="nav-link dropdown-toggle d-flex align-items-center"
					data-bs-toggle="dropdown"
					style="height: 70px; display: flex; align-items: center;"> <span
						class="d-none d-md-inline me-2" style="margin-right: 8px;">이재현</span>
						<img src="/devmap/images/main/user2.png"
						class="rounded-circle shadow-sm" alt="User Image"
						style="width: 32px; height: 32px;" />
				</a>

					<ul class="dropdown-menu dropdown-menu-lg dropdown-menu-end"
						style="padding-top: 12px; width: 240px;">
						<li class="user-header text-center" style="padding: 10px;"><img
							src="/devmap/images/main/user2.png"
							class="rounded-circle shadow mb-2" alt="User Image"
							style="width: 80px; height: 80px;" />
							<p style="margin-top: 8px;">
								<b>이재현</b><br /> <small id="subject">2025-04-01</small>
							</p></li>

						<div class="mb-3 text-center">
							<span class="text-secondary"> <i
								class="fa-solid fa-trophy me-1"></i>Rank
							</span> <span class="fw-bold text-primary">1070</span>
						</div>

						<hr style="margin: 0 16px;" />

						<li class="user-footer d-flex justify-content-between px-3 pb-3"
							style="background-color: #f8f9fa; gap: 10px;"><a
							href="/devmap/user/mypage/mypage.do" class="btn"
							style="flex: 1; background-color: #ffffff; border: 1px solid #ccc; padding: 6px 0; font-size: 14px; border-radius: 5px; text-align: center; transition: 0.2s;">
								MyPage </a> <a href="${pageContext.request.contextPath}/user/logout"
							class="btn"
							style="flex: 1; background-color: #ffffff; border: 1px solid #ccc; padding: 6px 0; font-size: 14px; border-radius: 5px; text-align: center; transition: 0.2s;">
								Sign out </a></li>
					</ul></li>
			</c:if>
			<!--end::User Menu Dropdown-->
		</ul>
		<!--end::End Navbar Links-->
	</div>
	<!--end::Container-->
</nav>
