<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <!--begin::Head-->
  <head>
    <%@include file="/WEB-INF/views/inc/header.jsp" %>
    <!-- 추가: jQuery 스크립트 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  </head>
  <!--end::Head-->
  <!--begin::Body-->
  <body class="login-page bg-body-secondary">
    <div class="login-box">
      <div class="login-logo">
        <a href="../index2.html"><b>DevMap </b></a>
      </div>
      <!-- /.login-logo -->
      <div class="card">
        <div class="card-body login-card-body">
          <p class="login-box-msg">비밀번호 찾기</p>
          
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
          </c:if>
          
          <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
          </c:if>
          
          <form action="${pageContext.request.contextPath}/user/forgot-password" method="post" id="forgotPasswordForm">
            <div class="input-group mb-3">
              <input type="email" class="form-control" placeholder="가입한 이메일 주소" name="email" id="email" required />
              <div class="input-group-text"><span class="bi bi-envelope"></span></div>
            </div>
            
            <div class="row">
              <div class="col-12">
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary">비밀번호 재설정 링크 받기</button>
                </div>
              </div>
            </div>
          </form>
          
          <p class="mt-3 mb-1">
            <a href="${pageContext.request.contextPath}/user/login" class="text-center">로그인으로 돌아가기</a>
          </p>
          <p class="mb-0">
            <a href="${pageContext.request.contextPath}/user/register" class="text-center">회원가입 하기</a>
          </p>
        </div>
        <!-- /.login-card-body -->
      </div>
    </div>
    <!-- /.login-box -->
  </body>
  <!--end::Body-->
</html>
