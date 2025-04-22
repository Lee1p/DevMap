<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
  <!--begin::Head-->
  <head>
    <%@include file="/WEB-INF/views/inc/header.jsp" %>
    <!-- 추가: jQuery 스크립트 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    
    
  </head>
  <!--end::Head-->
  <!--begin::Body-->
   <body class="register-page bg-body-secondary">
    <div class="register-box">
      <div class="register-logo">
        <a href="/devmap/index.do"><b>DevMap </b></a>
      </div>
      <!-- /.register-logo -->
      <div class="card">
        <div class="card-body register-card-body">
          <p class="register-box-msg">회원가입 하기</p>
          <!-- 오류 메시지 표시 -->
			<c:if test="${not empty errorMessage}">
			    <div class="alert alert-danger">${errorMessage}</div>
			</c:if>
          <!-- 수정: 폼 액션 경로 변경 및 id 추가 -->
          <form action="${pageContext.request.contextPath}/user/register" method="post" id="registerForm">
            <div class="input-group mb-3">
              <input type="text" class="form-control" placeholder="이름" name="name" required />
              <div class="input-group-text"><span class="bi bi-person"></span></div>
            </div>
            <div class="input-group mb-3">
              <input type="email" class="form-control" placeholder="이메일" name="email" id="email" required />
              <div class="input-group-text"><span class="bi bi-envelope"></span></div>
            </div>
            <!-- 추가: 이메일 인증 버튼 -->
            <div class="input-group mb-3">
              <button type="button" id="sendVerificationBtn" class="btn btn-secondary">인증 코드 발송</button>
            </div>
            <!-- 추가: 인증 코드 입력 필드 (초기에는 숨김) -->
            <div class="input-group mb-3" id="verificationCodeGroup" style="display:none;">
              <input type="text" class="form-control" placeholder="인증 코드" id="verificationCode" name="verificationCode" />
              <div class="input-group-append">
                <button type="button" id="verifyCodeBtn" class="btn btn-secondary">인증 확인</button>
              </div>
            </div>
            <div class="input-group mb-3">
              <input type="password" class="form-control" placeholder="비밀번호" name="password" id="password" required />
              <div class="input-group-text"><span class="bi bi-lock-fill"></span></div>
            </div>
            <div class="input-group mb-3">
              <input type="password" class="form-control" placeholder="비밀번호 확인" name="confirmPassword" id="confirmPassword" required />
              <div class="input-group-text"><span class="bi bi-lock-fill"></span></div>
            </div>
            <!-- 추가: 전화번호 입력 필드 -->
            <!-- <div class="input-group mb-3">
              <input type="tel" class="form-control" placeholder="전화번호" name="phoneNumber" />
              <div class="input-group-text"><span class="bi bi-phone"></span></div>
            </div> -->
            <!--begin::Row-->
            <div class="row">
              <div class="col-8">
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" required />
                  <label class="form-check-label" for="flexCheckDefault">
                    약관에 동의합니다<a href="#">자세히</a>
                  </label>
                </div>
              </div>
              <!-- /.col -->
              <div class="col-4">
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary" id="registerBtn" disabled>가입하기</button>
                </div>
              </div>
              <!-- /.col -->
            </div>
            <!--end::Row-->
          </form>
         <%--  <div class="social-auth-links text-center mb-3 d-grid gap-2">
            <p>- OR -</p>
            <a href="${pageContext.request.contextPath}/user/google-login" class="btn btn-danger">
              <i class="bi bi-google me-2"></i> Google 계정으로 로그인
            </a>
            <div class="g-signin2" data-onsuccess="onSignIn"></div>
          </div> --%>
     <%--      <div id="g_id_onload"
			     data-client_id="${googleClientId}"
			     data-callback="handleCredentialResponse">
			</div> --%>
		<div class="g_id_signin" data-type="standard"></div>
          <!-- /.social-auth-links -->
          <p class="mb-0">
            <a href="${pageContext.request.contextPath}/user/login" class="text-center"> 이미 회원이십니까? </a>
          </p>
        </div>
        <!-- /.register-card-body -->
      </div>
    </div>
    
    <!-- <script>
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
		    
    <!-- /.register-box -->
    
    <!-- 기존 스크립트 유지 -->

    
    <!-- 추가: 회원가입 관련 스크립트 -->
    <script>
    $(document).ready(function() {
        // 비밀번호 유효성 검사
        /* $("#password, #confirmPassword").on('input', function() {
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
        }); */
     // 비밀번호 유효성 검사
// 비밀번호 유효성 검사
	$("#password, #confirmPassword").on('input', function() {
	    var password = $("#password").val();
	    var confirmPassword = $("#confirmPassword").val();
	    
	    if (password.length < 8) {
	        $("#password").addClass("is-invalid");
	        // 비밀번호 오류 메시지를 입력 그룹 다음에 추가
	        if ($("#password-error").length === 0) {
	            $("#password").closest('.input-group').after('<div id="password-error" class="invalid-feedback d-block">비밀번호는 최소 8자리 이상이어야 합니다.</div>');
	        }
	    } else {
	        $("#password").removeClass("is-invalid").addClass("is-valid");
	        $("#password-error").remove();
	    }
	    
	    if (password !== confirmPassword && confirmPassword.length > 0) {
	        $("#confirmPassword").addClass("is-invalid");
	        // 비밀번호 확인 오류 메시지 표시
	        if ($("#confirm-error").length === 0) {
	            $("#confirmPassword").closest('.input-group').after('<div id="confirm-error" class="invalid-feedback d-block">비밀번호가 일치하지 않습니다.</div>');
	        }
	    } else if (confirmPassword.length > 0) {
	        $("#confirmPassword").removeClass("is-invalid").addClass("is-valid");
	        $("#confirm-error").remove();
	    }
	});


        
        // 이메일 인증 코드 발송
        $("#sendVerificationBtn").click(function() {
            var email = $("#email").val();
            if (email) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/user/verify-email",
                    type: "POST",
                    data: { email: email, action: "send" },
                    success: function(data) {
                        if (data.success) {
                            $("#verificationCodeGroup").show();
                            alert("인증 코드가 발송되었습니다. 이메일을 확인해주세요.");
                        } else {
                            alert(data.message);
                        }
                    },
                    error: function() {
                        alert("서버와의 통신 중 오류가 발생했습니다.");
                    }
                });
            } else {
                alert("이메일을 입력해주세요.");
            }
        });

        // 인증 코드 확인
        $("#verifyCodeBtn").click(function() {
            var email = $("#email").val();
            var code = $("#verificationCode").val();
            
            $.ajax({
                url: "${pageContext.request.contextPath}/user/verify-email",
                type: "POST",
                data: { email: email, code: code, action: "verify" },
                success: function(data) {
                    if (data.success) {
                        alert("이메일 인증이 완료되었습니다.");
                        $("#registerBtn").prop("disabled", false);
                    } else {
                        alert(data.message);
                    }
                },
                error: function() {
                    alert("서버와의 통신 중 오류가 발생했습니다.");
                }
            });
        });
    });
    </script>
  </body>
  <!--end::Body-->
</html>
