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
          <p class="login-box-msg">새 비밀번호 설정</p>
          
          <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
          </c:if>
          
          <form action="${pageContext.request.contextPath}/user/reset-password" method="post" id="resetPasswordForm">
            <input type="hidden" name="token" value="${param.token}" />
            <input type="hidden" name="email" value="${param.email}" />
            
            <div class="input-group mb-3">
              <input type="password" class="form-control" placeholder="새 비밀번호" name="password" id="password" required />
              <div class="input-group-text"><span class="bi bi-lock-fill"></span></div>
            </div>
            <div class="input-group mb-3">
              <input type="password" class="form-control" placeholder="새 비밀번호 확인" name="confirmPassword" id="confirmPassword" required />
              <div class="input-group-text"><span class="bi bi-lock-fill"></span></div>
            </div>
            
            <div class="row">
              <div class="col-12">
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary">비밀번호 변경</button>
                </div>
              </div>
            </div>
          </form>
          
          <p class="mt-3 mb-1">
            <a href="${pageContext.request.contextPath}/user/login" class="text-center">로그인으로 돌아가기</a>
          </p>
        </div>
        <!-- /.login-card-body -->
      </div>
    </div>
    <!-- /.login-box -->
    
    <script>
    $(document).ready(function() {
        // 비밀번호 유효성 검사
        $("#password, #confirmPassword").on('input', function() {
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            
            if (password.length < 8) {
                $("#password").addClass("is-invalid");
            } else {
                $("#password").removeClass("is-invalid").addClass("is-valid");
            }
            
            if (password !== confirmPassword) {
                $("#confirmPassword").addClass("is-invalid");
            } else {
                $("#confirmPassword").removeClass("is-invalid").addClass("is-valid");
            }
        });
        
        // 폼 제출 전 유효성 검사
        $("#resetPasswordForm").submit(function(e) {
            var password = $("#password").val();
            var confirmPassword = $("#confirmPassword").val();
            
            if (password.length < 8) {
                e.preventDefault();
                alert("비밀번호는 8자 이상이어야 합니다.");
                return;
            }
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert("비밀번호가 일치하지 않습니다.");
                return;
            }
        });
    });
    </script>
  </body>
  <!--end::Body-->
</html>
