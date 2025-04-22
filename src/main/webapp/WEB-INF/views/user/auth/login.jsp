<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <!--begin::Head-->
  <head>
    <%@include file="/WEB-INF/views/inc/header.jsp" %>
    <!-- 추가: Google 로그인 스크립트 -->
    <!-- <meta name="google-signin-client_id" content="724775783287-v7t6bcs4fk639s9i2p5lh47nngd93ej0.apps.googleusercontent.com"> -->
	<script src="https://accounts.google.com/gsi/client" async defer></script>
	
	<style>
	:root {
	/* 소프트한 그라데이션 컬러 */
	--gradient-1: #f5f7fa;
	--gradient-2: #c3cfe2;
	/* 메인 컬러: 부드러운 블루 계열 */
	--primary: #6d9feb;
	--primary-light: #b4d0ff;
	--primary-dark: #4c7bd9;
	/* 포인트 컬러: 친근한 코럴 계열 */
	--accent: #ff7e67;
	--accent-light: #ffbfb3;
	--accent-dark: #e05e4b;
	/* 중립 톤 */
	--neutral-50: #f9fafb;
	--neutral-100: #f3f4f6;
	--neutral-200: #e5e7eb;
	--neutral-300: #d2d5db;
	--neutral-600: #6b7280;
	--neutral-700: #374151;
	--neutral-800: #1f2937;
	/* 그림자 */
	--shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.04);
	--shadow-md: 0 8px 16px rgba(0, 0, 0, 0.06);
	--shadow-lg: 0 16px 24px rgba(0, 0, 0, 0.08);
	/* 라운드 처리 */
	--radius-sm: 10px;
	--radius-md: 16px;
	--radius-lg: 24px;
	--radius-xl: 32px;
}

/* 배경 요소 */
.bg-element {
	position: absolute;
	border-radius: 50%;
	filter: blur(80px);
	opacity: 0.4;
	z-index: -1;
}

.bg-element-1 {
	width: 400px;
	height: 400px;
	background: var(--primary-light);
	top: -100px;
	left: -150px;
}

.bg-element-2 {
	width: 350px;
	height: 350px;
	background: var(--accent-light);
	bottom: 100px;
	right: -100px;
}
	
	
	
	</style>
	
  </head>
  <!--end::Head-->
  <!--begin::Body-->
  <body class="login-page bg-body-secondary">
  <div class="bg-element bg-element-1"></div>
<div class="bg-element bg-element-2"></div>
    <div class="login-box">
      <div class="login-logo">
        <!-- <a href="../index2.html"> --><a href="/devmap/index.do"><b>DevMap </b></a>
      </div>
      <!-- /.login-logo -->
      <div class="card">
        <div class="card-body login-card-body">
          <p class="login-box-msg">로그인</p>
          
           <!-- 오류 메시지 표시 영역 추가 -->
			 <c:if test="${not empty errorMessage}">
			   <div class="alert alert-danger">${errorMessage}</div>
			 </c:if>
			 
          <!-- 수정: 폼 액션 경로 변경 -->
          <form action="${pageContext.request.contextPath}/user/login" method="post">
            <div class="input-group mb-3">
              <input type="email" class="form-control" placeholder="이메일" name="email" required />
              <div class="input-group-text"><span class="bi bi-envelope"></span></div>
            </div>
            <div class="input-group mb-3">
              <input type="password" class="form-control" placeholder="비밀번호" name="password" required />
              <div class="input-group-text"><span class="bi bi-lock-fill"></span></div>
            </div>
            <!--begin::Row-->
            <div class="row">
              <div class="col-8">
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" />
                  <label class="form-check-label" for="flexCheckDefault"> 로그인 상태 유지 </label>
                </div>
              </div>
              <!-- /.col -->
              <div class="col-4">
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary">로그인</button>
                </div>
              </div>
              <!-- /.col -->
            </div>
            <!--end::Row-->
          </form>
          <%-- <div class="social-auth-links text-center mb-3 d-grid gap-2">
            <p>- OR -</p>
            <!-- 수정: Google 로그인 버튼 -->
           	<a href="${pageContext.request.contextPath}/user/google-login" class="btn btn-danger">
			  <i class="bi bi-google me-2"></i> Google 계정으로 로그인
			</a>
			<div class="g-signin2" data-onsuccess="onSignIn"></div> --%>
		<%-- 	<div id="g_id_onload"
			     data-client_id="${googleClientId}"
			     data-callback="handleCredentialResponse">
			</div> --%>
			<div class="g_id_signin" data-type="standard"></div>
          </div>
          <!-- /.social-auth-links -->
          <p class="mb-1" style="padding-left: 18px; padding-bottom: 3px;" >
          	<a href="${pageContext.request.contextPath}/user/forgot-password">비밀번호 찾기</a>
          </p>
          <p class="mb-0" style="padding-left: 18px; padding-bottom: 10px;">
            <!-- 수정: 회원가입 링크 경로 변경 -->
            <a href="${pageContext.request.contextPath}/user/register" class="text-center">회원가입 하기</a>
          </p>
        </div>
        <!-- /.login-card-body -->
      </div>
    
<!--     <script>
		function handleCredentialResponse(response) {
		  // 기존의 onSignIn 함수 내용을 여기로 옮깁니다
		  var id_token = response.credential;
		  
		  $.ajax({
		    url: '${pageContext.request.contextPath}/user/google-login',
		    type: 'POST',
		    data: {
		      idToken: id_token
		    },
		    success: function(response) {
		      if (response.success) {
		        window.location.href = response.redirect;
		      } else {
		        alert(response.message);
		      }
		    },
		    error: function() {
		      alert('Google 로그인 처리 중 오류가 발생했습니다.');
		    }
		  });
		}
</script> -->
    
    <!-- /.login-box -->
    

    
  
  </body>
  <!--end::Body-->
</html>
